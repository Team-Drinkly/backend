package com.drinkhere.apiauth.presentation;

import com.drinkhere.apiauth.service.LogoutUseCase;
import com.drinkhere.apiauth.service.OAuthUseCase;
import com.drinkhere.apiauth.service.ReissueUseCase;
import com.drinkhere.domainrds.auth.consts.AuthConsts;
import com.drinkhere.domainrds.auth.dto.OAuthResponse;
import com.drinkhere.domainrds.auth.dto.TokenReissueResponse;
import com.drinkhere.domainrds.auth.enums.Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthUseCase oAuthUseCase;
    private final LogoutUseCase logoutUseCase;
    private final ReissueUseCase reissueUseCase;

    @GetMapping("/oauth/{provider}")
    public OAuthResponse oAuthLogin(@PathVariable Provider provider,
                                    @RequestHeader("Authorization") String accessToken
    ) {
        return oAuthUseCase.oAuthLogin(provider, accessToken);
    }

    @PostMapping("/reissue")
    public TokenReissueResponse reissue(@RequestHeader(AuthConsts.REFRESH_TOKEN_HEADER) String refreshToken) {
        return reissueUseCase.reissue(refreshToken);
    }


    @DeleteMapping("/logout")
    public void logout(@RequestHeader(AuthConsts.REFRESH_TOKEN_HEADER) String refreshToken) {
        logoutUseCase.logoutAccessUser(refreshToken);
    }

}
