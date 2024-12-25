package com.drinkhere.apistore.response;

import com.drinkhere.common.response.BaseSuccessStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StoreSuccessStatus implements BaseSuccessStatus {
    GET_COORDINATES_SUCCESS(HttpStatus.OK, "주소 to 좌표 변환 성공"),
    CREATE_STORE_SUCCESS(HttpStatus.CREATED, "제휴 매장 등록 성공")
    ;

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public int getStatusCode() {
        return this.httpStatus.value();
    }
}