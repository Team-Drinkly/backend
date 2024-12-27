package com.drinkhere.apipayment.presentation;

import com.drinkhere.common.response.BaseSuccessStatus;
import org.springframework.http.HttpStatus;

public enum PaymentSuccessStatus implements BaseSuccessStatus {

    BILLING_KEY_ISSUED(HttpStatus.OK, "Billing key issued successfully", 200),
    PAYMENT_APPROVED(HttpStatus.OK, "Payment approved successfully", 200),
    PAYMENT_CANCELLED(HttpStatus.OK, "Payment cancelled successfully", 200);

    private final HttpStatus httpStatus;
    private final String message;
    private final int statusCode;

    PaymentSuccessStatus(HttpStatus httpStatus, String message, int statusCode) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.statusCode = statusCode;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }
}