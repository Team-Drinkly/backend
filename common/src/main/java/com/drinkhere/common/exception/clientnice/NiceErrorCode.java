package com.drinkhere.common.exception.clientnice;

import com.drinkhere.common.exception.BaseErrorCode;
import com.drinkhere.common.response.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
public enum NiceErrorCode implements BaseErrorCode {

    // 인증 관련 오류 (토큰 처리)
    INVALID_TOKEN("유효하지 않은 토큰입니다.", 1000, HttpStatus.BAD_REQUEST),
    CRYPTO_TOKEN_REQUEST_FAILED("NICE API 암호화 토큰 요청에 실패했습니다.", 1001, HttpStatus.BAD_REQUEST),
    CRYPTO_TOKEN_FETCH_FAILED("암호화 토큰 조회에 실패했습니다.", 1004, HttpStatus.INTERNAL_SERVER_ERROR),

    // 암호화/복호화 관련 오류
    DECRYPTION_FAILED("데이터 복호화에 실패했습니다.", 1003, HttpStatus.INTERNAL_SERVER_ERROR),
    ENCRYPTION_FAILED("암호화에 실패했습니다.", 1003, HttpStatus.INTERNAL_SERVER_ERROR),
    INTEGRITY_VALUE_GENERATION_FAILED("무결성값 생성에 실패했습니다.", 1004, HttpStatus.INTERNAL_SERVER_ERROR),
    SHA256_ALGORITHM_NOT_FOUND("SHA-256 알고리즘을 찾을 수 없습니다.", 1006, HttpStatus.INTERNAL_SERVER_ERROR),

    // Redis 관련 오류
    REDIS_SAVE_FAILED("Redis에 데이터를 저장하는 데 실패했습니다.", 1005, HttpStatus.INTERNAL_SERVER_ERROR),
    CRYPTO_DATA_NOT_FOUND("Redis에 암호화 데이터를 찾을 수 없습니다.", 1002, HttpStatus.NOT_FOUND),
    REQUEST_NO_NOT_FOUND("Redis에 RequestNo를 찾을 수 없습니다.", 1001, HttpStatus.NOT_FOUND),
    REQUEST_NO_MISMATCH("RequestNo가 일치하지 않습니다.", 1002, HttpStatus.BAD_REQUEST),

    // JSON 처리 관련 오류
    JSON_PROCESSING_FAILED("JSON 직렬화에 실패했습니다.", 1007, HttpStatus.BAD_REQUEST),
    PARSING_FAILED("JSON 데이터를 파싱하는 데 실패했습니다.", 1006, HttpStatus.BAD_REQUEST);

    private final String message;
    private final int errorCode;
    private final HttpStatus httpStatus;

    @Override
    public ResponseEntity<ApiResponse<Void>> toResponseEntity() {
        return ApiResponse.fail(httpStatus, message);
    }
}
