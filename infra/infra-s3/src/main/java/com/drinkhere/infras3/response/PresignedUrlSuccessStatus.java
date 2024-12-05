package com.drinkhere.infras3.response;

import com.drinkhere.common.response.BaseSuccessStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PresignedUrlSuccessStatus  implements BaseSuccessStatus {
    CREATE_PRESIGNED_SUCCESS(HttpStatus.CREATED, "PUT 위한 Presigned URL 생성 성공");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public int getStatusCode() {
        return this.httpStatus.value();
    }
}
