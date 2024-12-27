package com.drinkhere.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private final int status;
    private final boolean success;
    private final String message;
    private T data;

    /**
     * success 응답 처리
     */
    public static <T> ResponseEntity<ApiResponse<T>> success(BaseSuccessStatus successStatus) {
        return ResponseEntity.status(successStatus.getHttpStatus())
                .body(ApiResponse.<T>builder()
                        .status(successStatus.getStatusCode())
                        .success(true)
                        .message(successStatus.getMessage()).build());
    }
    public static <T> ResponseEntity<ApiResponse<T>> success(BaseSuccessStatus successStatus, T data) {
        return ResponseEntity.status(successStatus.getHttpStatus())
                .body(ApiResponse.<T>builder()
                        .status(successStatus.getStatusCode())
                        .success(true)
                        .message(successStatus.getMessage())
                        .data(data)
                        .build());
    }

    /**
     * fail 응답 처리
     */
    public static <T> ResponseEntity<ApiResponse<T>> fail(HttpStatus httpStatus, String message) {
        return ResponseEntity.status(httpStatus.value())
                .body(ApiResponse.<T>builder()
                        .status(httpStatus.value())
                        .success(false)
                        .message(message)
                        .build());
    }
}