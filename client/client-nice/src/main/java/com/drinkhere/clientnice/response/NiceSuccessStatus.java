package com.drinkhere.clientnice.response;

import com.drinkhere.common.response.BaseSuccessStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum NiceSuccessStatus implements BaseSuccessStatus {
    INIT_NICE_API_SUCCESS(HttpStatus.CREATED, "NICE API 표준창 요청 위한 초기화 성공"),
    PROCESS_CALLBACK_SUCCESS(HttpStatus.OK, "NICE API 인증 결과 콜백 처리 성공")
    ;

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public int getStatusCode() {
        return this.httpStatus.value();
    }
}