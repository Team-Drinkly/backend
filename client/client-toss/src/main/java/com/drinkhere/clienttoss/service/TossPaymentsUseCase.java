package com.drinkhere.clienttoss.service;

import com.drinkhere.clienttoss.dto.billing.BillingRequestDto;
import com.drinkhere.clienttoss.dto.billing.BillingResponseDto;
import com.drinkhere.clienttoss.dto.cancel.CancelRequestDto;
import com.drinkhere.clienttoss.dto.cancel.CancelResponseDto;
import com.drinkhere.clienttoss.dto.payment.PaymentRequestDto;
import com.drinkhere.clienttoss.dto.payment.PaymentResponseDto;
import com.drinkhere.clienttoss.webclient.config.TossProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class TossPaymentsUseCase {

    private final WebClient authorizeBillingWebClient;
    private final WebClient autoPaymentWebClient;
    private final WebClient cancelPaymentWebClient;
    private final TossProperties tossProperties;

    public BillingResponseDto issueBillingKeyAndAutoPay(BillingRequestDto billingRequestDto) {
        return authorizeBillingWebClient.post()
                .uri("")
                .header("Authorization", "Basic " + tossProperties.getSecret())
                .header("Content-Type", "application/json")
                .bodyValue(billingRequestDto)
                .retrieve()
                .bodyToMono(BillingResponseDto.class)
                .block();
    }

    public PaymentResponseDto performAutoPayment(PaymentRequestDto paymentRequestDto, String authHeader) {
        return autoPaymentWebClient.post()
                .uri("/" + paymentRequestDto.billingKey())
                .header("Authorization", "Basic " + authHeader)
                .header("Content-Type", "application/json")
                .bodyValue(paymentRequestDto)
                .retrieve()
                .bodyToMono(PaymentResponseDto.class)
                .block();
    }

    public CancelResponseDto cancelPayment(CancelRequestDto cancelRequestDto, String authHeader) {
        return cancelPaymentWebClient.post()
                .uri("/" + cancelRequestDto.paymentKey() + "/cancel")
                .header("Authorization", "Basic " + authHeader)
                .header("Content-Type", "application/json")
                .bodyValue(cancelRequestDto)
                .retrieve()
                .bodyToMono(CancelResponseDto.class)
                .block();
    }
}