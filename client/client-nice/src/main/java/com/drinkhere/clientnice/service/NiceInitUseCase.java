package com.drinkhere.clientnice.service;

import com.drinkhere.clientnice.dto.NiceCryptoData;
import com.drinkhere.clientnice.dto.NiceRequestData;
import com.drinkhere.clientnice.dto.response.GetCryptoTokenResponse;
import com.drinkhere.clientnice.dto.response.CreateNiceApiRequestDataDto;
import com.drinkhere.clientnice.webclient.NiceClient;
import com.drinkhere.clientnice.webclient.config.NiceProperties;
import com.drinkhere.common.exception.clientnice.NiceErrorCode;
import com.drinkhere.common.exception.clientnice.NiceException;
import com.drinkhere.infraredis.util.RedisUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Base64Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

import static com.drinkhere.common.exception.clientnice.NiceErrorCode.*;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

@Service
@RequiredArgsConstructor
public class NiceInitUseCase {
    private static final Logger logger = LoggerFactory.getLogger(NiceInitUseCase.class);

    private final NiceClient niceClient;
    private final RedisUtil redisUtil;
    private final ObjectMapper objectMapper;
    private final NiceProperties niceProperties;
    public CreateNiceApiRequestDataDto initNiceApi(Long memberId) {

        String key = null;
        String iv = null;
        String hmacKey = null;
        GetCryptoTokenResponse cryptoToken = fetchCryptoTokenFromCache();
        // 암호화 토큰 조회
        if (cryptoToken == null) {
            String reqDtim = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String reqNo = UUID.randomUUID().toString().substring(0, 30);

            // 새 암호화 토큰 발급 및 대칭키&무결성키 생성
            cryptoToken = requestNewCryptoToken(reqDtim,reqNo);
            String resultVal = generateResultVal(reqDtim, reqNo, cryptoToken.dataBody().tokenVal());
            key = resultVal.substring(0, 16);
            iv = resultVal.substring(resultVal.length() - 16);
            hmacKey = resultVal.substring(0, 32);


            saveCryptoTokenToRedis(cryptoToken);
            saveCryptoDataToRedis(key, iv, hmacKey);
        } else {
            // 기존 암호화 토큰으로 생성한 키 값 조회
            NiceCryptoData niceCryptoData = fetchCryptoDataFromCache();
            if (niceCryptoData != null) {
                key = niceCryptoData.key();
                iv = niceCryptoData.iv();
                hmacKey = niceCryptoData.hmacKey();
            }
        }
        System.out.println("cryptoToken.toString() = " + cryptoToken.toString());

        // 요청 데이터 생성 및 암호화
        String reqData = createReqDataJson(cryptoToken.dataBody().siteCode(), memberId);
        String encData = encryptReqData(key, iv, reqData);
        
        // Hmac 무결성 체크값 생성
        String integrityValue = generateIntegrityValue(hmacKey.getBytes(), encData.getBytes());

        return new CreateNiceApiRequestDataDto(
                cryptoToken.dataBody().tokenVersionId(),
                encData,
                integrityValue
        );
    }

    /**----------------------------------------------------------------------------------------------------**/
    private GetCryptoTokenResponse fetchCryptoTokenFromCache() {
        String storedJson = (String) redisUtil.get("cryptoToken");
        if (storedJson != null) {
            return readValueWithExceptionHandling(storedJson, GetCryptoTokenResponse.class);
        }
        return null;
    }

    private NiceCryptoData fetchCryptoDataFromCache() {
        String storedCryptoData = (String) redisUtil.get("cryptoData");
        if (storedCryptoData != null) {
            return readValueWithExceptionHandling(storedCryptoData, NiceCryptoData.class);
        }
        return null;
    }

    private GetCryptoTokenResponse requestNewCryptoToken(String reqDtim, String reqNo) {
        try {
            return niceClient.requestCryptoToken(reqDtim, reqNo);
        } catch (Exception e) {
            logger.error("Error while requesting new crypto token", e);
            throw new NiceException(CRYPTO_TOKEN_REQUEST_FAILED);
        }
    }

    private void saveCryptoTokenToRedis(GetCryptoTokenResponse cryptoToken) {
        try {
            String jsonValue = objectMapper.writeValueAsString(cryptoToken);
            redisUtil.saveAsValue("cryptoToken", jsonValue, 3300L, SECONDS);
        } catch (Exception e) {
            logger.error("Error while saving crypto token to Redis", e);
            throw new NiceException(REDIS_SAVE_FAILED);
        }
    }

    // 암호화 시 필요한 key, iv, hmacKey 저장
    private void saveCryptoDataToRedis(String key, String iv, String hmacKey) {
        try {
            NiceCryptoData niceCryptoData = NiceCryptoData.of(key, iv, hmacKey);
            String cryptoDataJson = objectMapper.writeValueAsString(niceCryptoData);
            redisUtil.saveWithoutExpiration("cryptoData", cryptoDataJson);
        } catch (Exception e) {
            logger.error("Error while saving crypto data to Redis", e);
            throw new NiceException(REDIS_SAVE_FAILED);
        }
    }

    private String generateResultVal(String reqDtim, String reqNo, String tokenVal) {
        try {
            String value = reqDtim.trim() + reqNo.trim() + tokenVal.trim();
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(value.getBytes());
            byte[] arrHashValue = md.digest();
            return Base64.getEncoder().encodeToString(arrHashValue);
        } catch (NoSuchAlgorithmException e) {
            logger.error("Error while generating result value", e);
            throw new NiceException(SHA256_ALGORITHM_NOT_FOUND);
        }
    }

    private String createReqDataJson(String siteCode, Long memberId) {
        try {
            String requestNo = UUID.randomUUID().toString().substring(0, 30);
            String returnUrl = niceProperties.getCallbackUrl() + "/api/v1/public/nice/call-back?mid=" + memberId;
            NiceRequestData niceRequestData = new NiceRequestData(
                    requestNo,
                    returnUrl,
                    siteCode,
                    "Y"
            );

            redisUtil.saveAsValue("memberId:" + memberId + ":requestNo", requestNo, 30L, MINUTES);
            return objectMapper.writeValueAsString(niceRequestData);
        } catch (JsonProcessingException e) {
            throw new NiceException(JSON_PROCESSING_FAILED);
        }
    }

    public String encryptReqData(String key, String iv, String reqData) {
        try {
            SecretKey secureKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(iv.getBytes()));
            byte[] encrypted = cipher.doFinal(reqData.trim().getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            logger.error("Error while encrypting request data", e);
            throw new NiceException(ENCRYPTION_FAILED);
        }
    }
    private String generateIntegrityValue(byte[] secretKey, byte[] encData) {
        byte[] hmac256 = null;
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec sks = new SecretKeySpec(secretKey, "HmacSHA256");
            mac.init(sks);
            hmac256 = mac.doFinal(encData);
            return Base64.getEncoder().encodeToString(hmac256);
        } catch (Exception e) {
            logger.error("Error while generating integrity value", e);
            throw new NiceException(INTEGRITY_VALUE_GENERATION_FAILED);
        }
    }
    private <T> T readValueWithExceptionHandling(String jsonValue, Class<T> valueType) {
        try {
            return objectMapper.readValue(jsonValue, valueType);
        } catch (Exception e) {
            logger.error("Error while parsing JSON", e);
            throw new NiceException(PARSING_FAILED); // Throw custom exception
        }
    }
}
