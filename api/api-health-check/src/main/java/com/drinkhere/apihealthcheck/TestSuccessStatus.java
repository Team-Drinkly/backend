package com.drinkhere.apihealthcheck;

import com.drinkhere.common.response.BaseSuccessStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TestSuccessStatus implements BaseSuccessStatus {
    GET_PRESIGNED_SUCCESS(HttpStatus.OK, "GET 위한 Presigned URL 조회 성공");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public int getStatusCode() {
        return this.httpStatus.value();
    }
}
