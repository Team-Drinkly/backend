package com.drinkhere.clienttoss.dto.payment;

public record PaymentResponseDto(String paymentKey, String orderId, String orderName, String requestedAt, String approvedAt, int amount, int totalAmount) {
}