package com.drinkhere.clientgeocoding.response;

import com.drinkhere.common.response.BaseSuccessStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StoreSuccessStatus implements BaseSuccessStatus {
    GET_COORDINATES_SUCCESS(HttpStatus.OK, "주소 to 좌표 변환 성공");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public int getStatusCode() {
        return this.httpStatus.value();
    }
}