package com.drinkhere.clientnice.service;

import com.drinkhere.clientnice.dto.NiceCryptoData;
import com.drinkhere.clientnice.dto.NiceDecryptedData;
import com.drinkhere.common.annotation.ApplicationService;
import com.drinkhere.common.exception.clientnice.NiceException;
import com.drinkhere.domainrds.member.entity.Member;
import com.drinkhere.domainrds.member.enums.Gender;
import com.drinkhere.domainrds.member.enums.MobileCo;
import com.drinkhere.domainrds.member.enums.NationalInfo;
import com.drinkhere.domainrds.member.service.MemberCommandService;
import com.drinkhere.infraredis.util.RedisUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static com.drinkhere.common.exception.clientnice.NiceErrorCode.*;

@ApplicationService
@RequiredArgsConstructor
public class NiceCallBackUseCase {
    private final RedisUtil redisUtil;
    private final ObjectMapper objectMapper;
    private final MemberCommandService memberCommandService;
    private static final String REDIS_CRYPTO_KEY = "cryptoData";
    private static final String REDIS_REQUEST_NO_KEY_TEMPLATE = "memberId:%d:requestNo";
    private static final String SYMMETRIC_ENCRYPTION_ALGORITHM_AES = "AES";
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

    public void processCallback(Long memberId, String encData) throws UnsupportedEncodingException {
        // 대칭키 조회
        NiceCryptoData niceCryptoData = getCryptoDataFromRedisAndValidate();

        // encData 복호화
        NiceDecryptedData niceDecryptedData = decryptAndParseData(encData, niceCryptoData.key(), niceCryptoData.iv());

        // Redis에서 requestno 조회 후 복호화 결과의 requestno 비교
        getRequestNoFromRedisAndValidate(memberId, niceDecryptedData.requestNo());

        // 복호화 결과 저장
        Member member = Member.builder()
                .name(URLDecoder.decode(niceDecryptedData.utf8Name(), "UTF-8"))
                .birthDate(niceDecryptedData.birthDate())
                .gender(Gender.fromValue(Integer.parseInt(niceDecryptedData.gender()))) // Gender Enum 변환
                .nationalInfo(NationalInfo.fromValue(Integer.parseInt(niceDecryptedData.nationalInfo()))) // NationalInfo Enum 변환
                .mobileCo(MobileCo.fromValue(Integer.parseInt(niceDecryptedData.mobileCo()))) // MobileCo Enum 변환
                .mobileNo(niceDecryptedData.mobileNo())
                .build();

        memberCommandService.save(member);
    }

    /**--------------------------------------------------------------------------------------------**/
    // Redis에서 대칭키 조회 후 역직렬화
    private NiceCryptoData getCryptoDataFromRedisAndValidate() {
        String cryptoDataJson = (String) redisUtil.get(REDIS_CRYPTO_KEY);

        if (cryptoDataJson == null) {
            throw new NiceException(CRYPTO_DATA_NOT_FOUND);
        }

        try {
            return objectMapper.readValue(cryptoDataJson, NiceCryptoData.class); // Deserialization
        } catch (JsonProcessingException e) {
            throw new NiceException(DESERIALIZATION_FAILED);
        }
    }

    // 복호화 후 객체 반환
    private NiceDecryptedData decryptAndParseData(String encData, String key, String iv) {
        try {
            SecretKey secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), SYMMETRIC_ENCRYPTION_ALGORITHM_AES );
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8)));

            byte[] decodedData = Base64.getDecoder().decode(encData);
            byte[] decryptedData = cipher.doFinal(decodedData);

            String decryptedString = new String(decryptedData, StandardCharsets.UTF_8);

            return objectMapper.readValue(decryptedString, NiceDecryptedData.class);// Deserialization
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new NiceException(INVALID_CIPHER_ALGORITHM);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new NiceException(INVALID_CIPHER_PARAMETERS);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new NiceException(CIPHER_DECRYPTION_FAILED);
        } catch (JsonProcessingException e) {
            throw new NiceException(DESERIALIZATION_FAILED);
        }

    }

    // requestno 조회 및 검증
    private String getRequestNoFromRedisAndValidate(Long memberId, String niceDecryptedRequestNo) {
        String requestNoKey = String.format(REDIS_REQUEST_NO_KEY_TEMPLATE, memberId);
        String requestNoFromRedis = (String) redisUtil.get(requestNoKey);
        if (requestNoFromRedis != null) { // 레디스에 해당 요청번호가 존재하는지
            if (!niceDecryptedRequestNo.equals(requestNoFromRedis)) { // 요청 번호가 일치하는지
                throw new NiceException(REQUEST_NO_MISMATCH);
            }
        } else {
            throw new NiceException(REQUEST_NO_NOT_FOUND);
        }

        return requestNoFromRedis;
    }
}
