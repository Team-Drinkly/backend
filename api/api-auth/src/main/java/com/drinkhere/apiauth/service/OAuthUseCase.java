package com.drinkhere.apiauth.service;

import com.drinkhere.domainrds.auth.dto.OAuthResponse;
import com.drinkhere.domainrds.auth.enums.Provider;

public interface OAuthUseCase {

    OAuthResponse oAuthLogin(Provider provider, String accessToken);

}
