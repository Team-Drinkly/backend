package com.drinkhere.apiauth.service.impl;

import com.drinkhere.apiauth.service.OAuthUseCase;
import com.drinkhere.apiauth.service.oauth.OAuthInvoker;
import com.drinkhere.common.annotation.ApplicationService;
import com.drinkhere.domainrds.auth.dto.OAuthRequest;
import com.drinkhere.domainrds.auth.dto.OAuthResponse;
import com.drinkhere.domainrds.auth.enums.Provider;
import lombok.RequiredArgsConstructor;

@ApplicationService
@RequiredArgsConstructor
public class OAuthUseCaseImpl implements OAuthUseCase {

    private final OAuthInvoker oAuthInvoker;

    @Override
    public OAuthResponse oAuthLogin(Provider provider, String accessToken) {
        return oAuthInvoker.execute(new OAuthRequest(provider, accessToken));
    }
}
