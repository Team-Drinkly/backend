package com.drinkhere.clienttoss.service;

import com.drinkhere.clienttoss.dto.billing.BillingRequestDto;
import com.drinkhere.clienttoss.dto.billing.BillingResponseDto;
import com.drinkhere.clienttoss.dto.cancel.CancelRequestDto;
import com.drinkhere.clienttoss.dto.cancel.CancelResponseDto;
import com.drinkhere.clienttoss.dto.payment.PaymentRequestDto;
import com.drinkhere.clienttoss.dto.payment.PaymentResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TossPaymentsUseCase {

    private final WebClient authorizeBillingWebClient;
    private final WebClient autoPaymentWebClient;
    private final WebClient cancelPaymentWebClient;

    public TossPaymentsUseCase(WebClient authorizeBillingWebClient, WebClient autoPaymentWebClient, WebClient cancelPaymentWebClient) {
        this.authorizeBillingWebClient = authorizeBillingWebClient;
        this.autoPaymentWebClient = autoPaymentWebClient;
        this.cancelPaymentWebClient = cancelPaymentWebClient;
    }

    public Mono<BillingResponseDto> issueBillingKeyAndAutoPay(BillingRequestDto billingRequestDto, String authHeader) {
        return authorizeBillingWebClient.post()
                .uri("")
                .header("Authorization", "Basic " + authHeader)
                .header("Content-Type", "application/json")
                .bodyValue(billingRequestDto)
                .retrieve()
                .bodyToMono(BillingResponseDto.class);
    }

    public Mono<PaymentResponseDto> performAutoPayment(PaymentRequestDto paymentRequestDto, String authHeader) {
        return autoPaymentWebClient.post()
                .uri("/" + paymentRequestDto.billingKey())
                .header("Authorization", "Basic " + authHeader)
                .header("Content-Type", "application/json")
                .bodyValue(paymentRequestDto)
                .retrieve()
                .bodyToMono(PaymentResponseDto.class);
    }

    public Mono<CancelResponseDto> cancelPayment(CancelRequestDto cancelRequestDto, String authHeader) {
        return cancelPaymentWebClient.post()
                .uri("/" + cancelRequestDto.paymentKey() + "/cancel")
                .header("Authorization", "Basic " + authHeader)
                .header("Content-Type", "application/json")
                .bodyValue(cancelRequestDto)
                .retrieve()
                .bodyToMono(CancelResponseDto.class);
    }
}