package com.drinkhere.infras3.presentation;

import com.drinkhere.common.response.ApplicationResponse;
import com.drinkhere.infras3.application.PresignedUrlService;
import com.drinkhere.infras3.dto.request.GetPresignedUrlRequest;
import com.drinkhere.infras3.dto.response.GetPresignedUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/presigned-url")
@RequiredArgsConstructor
public class PresignedUrlController {
    private final PresignedUrlService presignedUrlService;

    @PostMapping
    public ApplicationResponse<GetPresignedUrlResponse> getPresignedUrl(GetPresignedUrlRequest getPresignedUrlRequest) {
        return ApplicationResponse.created(presignedUrlService.getPresignedUrlForPut(getPresignedUrlRequest));
    }
}
