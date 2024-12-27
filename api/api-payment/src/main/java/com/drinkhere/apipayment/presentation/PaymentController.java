package com.drinkhere.apipayment.presentation;

import com.drinkhere.clienttoss.dto.billing.BillingRequestDto;
import com.drinkhere.clienttoss.dto.billing.BillingResponseDto;
import com.drinkhere.clienttoss.service.TossPaymentsUseCase;
import com.drinkhere.common.response.ApplicationResponse;
import com.drinkhere.common.response.ApplicationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final TossPaymentsUseCase tossPaymentsUseCase;

    // billing key 발급
    @PostMapping("/billing-key")
    public ApplicationResponse<BillingResponseDto> getBillingKey(
            @RequestBody BillingRequestDto requestDto
    ) {
        BillingResponseDto responseDto = tossPaymentsUseCase.issueBillingKeyAndAutoPay(requestDto);
        return ApplicationResponse.ok(responseDto);
    }

    // 멤버십 자동 결제 승인

    // 멤버십 사용

    // 결제 취소
}
