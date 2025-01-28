package com.drinkhere.apiauth.presentation;

import com.drinkhere.clientnice.dto.response.CreateNiceApiRequestDataDto;
import com.drinkhere.clientnice.service.InitializeNiceUseCase;
import com.drinkhere.clientnice.service.NiceCallBackUseCase;
import com.drinkhere.common.response.ApplicationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/nice")
public class NiceController {
    private final InitializeNiceUseCase initializeNiceUseCase;
    private final NiceCallBackUseCase niceCallBackUseCase;

    @GetMapping("/{mid}")
    public ApplicationResponse<CreateNiceApiRequestDataDto> initNiceApi(
            @PathVariable("mid") Long memberId
    ) {
        return ApplicationResponse.ok(initializeNiceUseCase.initializeNiceApi(memberId));
    }

    @GetMapping("/call-back")
    public ApplicationResponse<String> handleNiceCallBack(
            @RequestParam("mid") Long memberId,
            @RequestParam("token_version_id") String tokenVersionId,
            @RequestParam("enc_data") String encData,
            @RequestParam("integrity_value") String integrityValue
    ) {
        niceCallBackUseCase.processCallback(memberId, encData);
        return ApplicationResponse.created("call-back url 생성");
    }

}
