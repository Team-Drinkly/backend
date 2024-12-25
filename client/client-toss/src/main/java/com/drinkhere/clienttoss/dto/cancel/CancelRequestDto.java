package com.drinkhere.clienttoss.dto.cancel;

public record CancelRequestDto(String paymentKey, String cancelReason) {
}