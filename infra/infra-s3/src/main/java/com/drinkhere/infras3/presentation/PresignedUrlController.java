package com.drinkhere.infras3.presentation;

import com.drinkhere.common.response.ApiResponse;
import com.drinkhere.infras3.application.PresignedUrlService;
import com.drinkhere.infras3.dto.request.GetPresignedUrlRequest;
import com.drinkhere.infras3.dto.response.GetPresignedUrlResponse;
import com.drinkhere.infras3.response.PresignedUrlSuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.drinkhere.infras3.response.PresignedUrlSuccessStatus.CREATE_PRESIGNED_SUCCESS;

@RestController
@RequestMapping("/api/v1/presigned-url")
@RequiredArgsConstructor
public class PresignedUrlController {
    private final PresignedUrlService presignedUrlService;
    @PostMapping
    public ResponseEntity<ApiResponse<GetPresignedUrlResponse>> getPresignedUrl(GetPresignedUrlRequest getPresignedUrlRequest) {
        return ApiResponse.success(
                CREATE_PRESIGNED_SUCCESS,
                presignedUrlService.getPresignedUrlForPut(getPresignedUrlRequest)
        );
    }
}
