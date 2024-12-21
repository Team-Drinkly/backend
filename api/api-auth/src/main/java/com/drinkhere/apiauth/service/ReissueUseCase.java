package com.drinkhere.apiauth.service;

import com.drinkhere.domainrds.auth.dto.TokenReissueResponse;

public interface ReissueUseCase {

    TokenReissueResponse reissue(String refreshToken);
}
