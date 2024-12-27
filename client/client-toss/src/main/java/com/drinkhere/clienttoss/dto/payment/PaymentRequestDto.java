package com.drinkhere.clienttoss.dto.payment;

public record PaymentRequestDto(String billingKey, String customerKey, String orderId, String orderName, int amount) {
}
