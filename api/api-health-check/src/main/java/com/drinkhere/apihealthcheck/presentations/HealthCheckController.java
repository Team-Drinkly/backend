package com.drinkhere.apihealthcheck.presentations;

import com.drinkhere.apihealthcheck.TestSuccessStatus;
import com.drinkhere.apihealthcheck.dto.request.GetPresignedUrlRequest;
import com.drinkhere.apihealthcheck.dto.response.GetPresignedUrlCollectionResponse;
import com.drinkhere.apihealthcheck.dto.response.GetPresignedUrlResponse;
import com.drinkhere.common.response.ApiResponse;
import com.drinkhere.infraredis.util.RedisUtil;
import com.drinkhere.infras3.application.PresignedUrlService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/health-check")
@RequiredArgsConstructor
public class HealthCheckController {

    private final RedisUtil redisUtil;
    private final PresignedUrlService presignedUrlService;

    @GetMapping
    public String healthCheck() {
        return "This Turn is Green!";
    }

    @GetMapping("/redis")
    public String redisConnectionCheck() {
        // Redis에서 'id1' 키로 값을 가져옵니다.
//        String value = redisUtil.getObjectByKey("id1", String.class);
        String value = (String) redisUtil.get("id1");
        System.out.println(value);
        // Redis에서 가져온 값이 있을 경우 반환, 없으면 기본 메시지 반환
        if (value != null) {
            return value;
        } else {
            return "No value found for key 'id1'";
        }
    }

    @PostMapping("/presigned")
    public ResponseEntity<ApiResponse<GetPresignedUrlResponse>> getPresignedUrl(
            @RequestBody GetPresignedUrlRequest getPresignedUrlRequest
    ) {
        System.out.println("getPresignedUrl.filePath() = " + getPresignedUrlRequest.fileName());

        String presignedUrlForGet = presignedUrlService.getPresignedUrlForGet( getPresignedUrlRequest.fileName());

        System.out.println("presignedUrlForGet = " + presignedUrlForGet);

        com.drinkhere.apihealthcheck.dto.response.GetPresignedUrlResponse getPresignedUrlResponse = new com.drinkhere.apihealthcheck.dto.response.GetPresignedUrlResponse(getPresignedUrlRequest.fileName());

        return ApiResponse.success(TestSuccessStatus.GET_PRESIGNED_SUCCESS, getPresignedUrlResponse);
    }

    @PostMapping("/presigned/collection")
    public ResponseEntity<ApiResponse<GetPresignedUrlCollectionResponse>> getPresignedUrlCollection(
            @RequestBody GetPresignedUrlRequest getPresignedUrlRequest
    ) {
        System.out.println("getPresignedUrl.filePath() = " + getPresignedUrlRequest.fileName());

        String presignedUrlForGet = presignedUrlService.getPresignedUrlForGet(getPresignedUrlRequest.fileName());

        System.out.println("presignedUrlForGet = " + presignedUrlForGet);
        ArrayList<String> list = new ArrayList<>();
        GetPresignedUrlCollectionResponse getPresignedUrlResponse = new GetPresignedUrlCollectionResponse(list);
        getPresignedUrlResponse.getFilePaths().add(getPresignedUrlRequest.fileName());
        getPresignedUrlResponse.getFilePaths().add(getPresignedUrlRequest.fileName());
        // 리스트를 포함한 ApiResponse 반환
        return ApiResponse.success(TestSuccessStatus.GET_PRESIGNED_SUCCESS, getPresignedUrlResponse);
    }


}
