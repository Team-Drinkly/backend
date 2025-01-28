package com.drinkhere.clienttoss.dto.cancel;

public record CancelResponseDto(String paymentKey, String cancelReason, String requestedAt, String approvedAt,
                                int cancelAmount) {
}
