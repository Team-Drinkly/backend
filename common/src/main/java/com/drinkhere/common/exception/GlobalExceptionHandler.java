package com.drinkhere.common.exception;

import com.drinkhere.common.exception.oauth.AuthException;
import com.drinkhere.common.exception.oauth.NotExistTokenException;
import com.drinkhere.common.exception.oauth.OAuthException;
import com.drinkhere.common.exception.oauth.OAuthNotFoundException;
import com.drinkhere.common.exception.token.ExpiredTokenException;
import com.drinkhere.common.exception.token.InvalidAuthorizationTypeException;
import com.drinkhere.common.exception.token.InvalidTokenException;
import com.drinkhere.common.exception.token.TokenException;
import com.drinkhere.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

/**
 * Controller Layer에서 발생하는 예외를 처리하는 것이지
 * Filter 단의 예외는 인식하지 못한다...!
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ApiResponse.fail(CommonErrorCode.INVALID_PARAMETER.getHttpStatus(), e.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<ApiResponse<Void>> handleNoSuchElementException(NoSuchElementException e) {
        return ApiResponse.fail(CommonErrorCode.NOT_FOUND.getHttpStatus(), e.getMessage());
    }

    @ExceptionHandler(AuthException.class)
    protected ResponseEntity<ApiResponse<Void>> handleAuthException(AuthException e) {
        return ApiResponse.fail(e.getErrorCode().getHttpStatus(), e.getErrorCode().getMessage());
    }

    @ExceptionHandler(NotExistTokenException.class)
    protected ResponseEntity<ApiResponse<Void>> handleNotExistTokenException(NotExistTokenException e) {
        return ApiResponse.fail(e.getErrorCode().getHttpStatus(), e.getErrorCode().getMessage());
    }

    @ExceptionHandler(OAuthException.class)
    protected ResponseEntity<ApiResponse<Void>> handleOAuthException(OAuthException e) {
        return ApiResponse.fail(e.getErrorCode().getHttpStatus(), e.getErrorCode().getMessage());
    }

    @ExceptionHandler(OAuthNotFoundException.class)
    protected ResponseEntity<ApiResponse<Void>> handleOAuthNotFoundException(OAuthNotFoundException e) {
        return ApiResponse.fail(e.getErrorCode().getHttpStatus(), e.getErrorCode().getMessage());
    }

    @ExceptionHandler(ExpiredTokenException.class)
    protected ResponseEntity<ApiResponse<Void>> handleExpiredTokenException(ExpiredTokenException e) {
        return ApiResponse.fail(e.getErrorCode().getHttpStatus(), e.getErrorCode().getMessage());
    }

    @ExceptionHandler(InvalidAuthorizationTypeException.class)
    protected ResponseEntity<ApiResponse<Void>> handleInvalidAuthorizationTypeException(InvalidAuthorizationTypeException e) {
        return ApiResponse.fail(e.getErrorCode().getHttpStatus(), e.getErrorCode().getMessage());
    }

    @ExceptionHandler(InvalidTokenException.class)
    protected ResponseEntity<ApiResponse<Void>> handleInvalidTokenException(InvalidTokenException e) {
        return ApiResponse.fail(e.getErrorCode().getHttpStatus(), e.getErrorCode().getMessage());
    }

    @ExceptionHandler(TokenException.class)
    protected ResponseEntity<ApiResponse<Void>> handleTokenException(TokenException e) {
        return ApiResponse.fail(e.getErrorCode().getHttpStatus(), e.getErrorCode().getMessage());
    }
}
