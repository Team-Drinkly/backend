package com.drinkhere.clientnice.presentation;

import com.drinkhere.clientnice.dto.response.CreateNiceApiRequestDataDto;
import com.drinkhere.clientnice.service.NiceCallBackUseCase;
import com.drinkhere.clientnice.service.NiceInitUseCase;
import com.drinkhere.common.response.ApiResponse;
import com.drinkhere.common.response.BaseSuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

import static com.drinkhere.clientnice.response.NiceSuccessStatus.INIT_NICE_API_SUCCESS;
import static com.drinkhere.clientnice.response.NiceSuccessStatus.PROCESS_CALLBACK_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/public/nice")
public class NiceController {
    private final NiceInitUseCase niceInitUseCase;
    private final NiceCallBackUseCase niceCallBackUseCase;
    @GetMapping("/{mid}")
    public ResponseEntity<ApiResponse<CreateNiceApiRequestDataDto>> initNiceApi(
            @PathVariable("mid") Long memberId
    ){
        return ApiResponse.success(INIT_NICE_API_SUCCESS, niceInitUseCase.initNiceApi(memberId));
    }

    @GetMapping("/call-back")
    public ResponseEntity<ApiResponse<String>> handleNiceCallBack(
            @RequestParam("mid") Long memberId,
            @RequestParam("token_version_id") String tokenVersionId,
            @RequestParam("enc_data") String encData,
            @RequestParam("integrity_value") String integrityValue
    ) throws UnsupportedEncodingException {
        // Call the use case to process the callback data
        niceCallBackUseCase.processCallback(memberId, encData);

        // Return a successful response
        return ApiResponse.success(PROCESS_CALLBACK_SUCCESS);
    }

}
