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

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static com.drinkhere.common.exception.clientnice.NiceErrorCode.*;

@ApplicationService
@RequiredArgsConstructor
public class NiceCallBackUseCase {
    private final RedisUtil redisUtil;
    private final ObjectMapper objectMapper;
    private final MemberCommandService memberCommandService;

    public void processCallback(Long memberId, String encData) throws UnsupportedEncodingException {
        // Redis에서 저장된 key, iv를 가져옴
        NiceCryptoData niceCryptoData = getCryptoDataFromRedis();

        String key = niceCryptoData.key();
        String iv = niceCryptoData.iv();

        // encData 복호화
        String decryptedData = decryptData(encData, key, iv);
        NiceDecryptedData niceDecryptedData = parseDecryptedData(decryptedData);
        String decodedUtf8Name = URLDecoder.decode(niceDecryptedData.utf8Name(), "UTF-8");

        // Redis에서 요청 번호 가져오기
        String requestNoFromRedis = (String) redisUtil.get("memberId:" + memberId + ":requestNo");

        if (requestNoFromRedis == null) {
            throw new NiceException(REQUEST_NO_NOT_FOUND); // 적절한 에러 코드로 교체
        }

        // 요청 번호가 일치하지 않는 경우
        if (!niceDecryptedData.requestNo().equals(requestNoFromRedis)) {
            throw new NiceException(REQUEST_NO_MISMATCH);
        }

        Member member = Member.builder()
                .name(decodedUtf8Name)
                .birthDate(niceDecryptedData.birthDate())
                .gender(Gender.fromValue(Integer.parseInt(niceDecryptedData.gender()))) // Gender Enum 변환
                .nationalInfo(NationalInfo.fromValue(Integer.parseInt(niceDecryptedData.nationalInfo()))) // NationalInfo Enum 변환
                .mobileCo(MobileCo.fromValue(Integer.parseInt(niceDecryptedData.mobileCo()))) // MobileCo Enum 변환
                .mobileNo(niceDecryptedData.mobileNo())
                .build();

        memberCommandService.save(member);

    }

    // Redis에서 암호화 관련 데이터 조회 후 역직렬화
    private NiceCryptoData getCryptoDataFromRedis() {
        String cryptoDataJson = (String) redisUtil.get("cryptoData");
        if (cryptoDataJson == null) {
            throw new NiceException(CRYPTO_DATA_NOT_FOUND);
        }

        try {
            return objectMapper.readValue(cryptoDataJson, NiceCryptoData.class);
        } catch (JsonProcessingException e) {
            throw new NiceException(PARSING_FAILED);
        }
    }

    // 조회한 암호화 관련 데이터(key, iv)로 복호화
    private String decryptData(String encData, String key, String iv) {
        try {
            SecretKey secureKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8)));

            byte[] decodedData = Base64.getDecoder().decode(encData);
            byte[] decryptedData = cipher.doFinal(decodedData);

            return new String(decryptedData, StandardCharsets.UTF_8); // 복호화된 데이터 반환
        } catch (Exception e) {
            throw new NiceException(DECRYPTION_FAILED);
        }
    }

    private NiceDecryptedData parseDecryptedData(String decryptedData) {
        try {
            return objectMapper.readValue(decryptedData, NiceDecryptedData.class);
        } catch (JsonProcessingException e) {
            throw new NiceException(PARSING_FAILED);
        }
    }
}
