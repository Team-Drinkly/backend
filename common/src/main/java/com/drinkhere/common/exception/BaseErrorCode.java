package com.drinkhere.common.exception;

import com.drinkhere.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface BaseErrorCode {

    HttpStatus getHttpStatus();
    String getMessage();

    ResponseEntity<ApiResponse<Void>> toResponseEntity();
}