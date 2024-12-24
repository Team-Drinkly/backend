package com.drinkhere.domainrds.auth.service;

import com.drinkhere.common.exception.oauth.AuthErrorCode;
import com.drinkhere.common.exception.oauth.OAuthNotFoundException;
import com.drinkhere.domainrds.auth.entity.OAuth;
import com.drinkhere.domainrds.auth.repository.OAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthQueryService {

    private final OAuthRepository oAuthRepository;

    public boolean existBySub(final String sub) {
        return oAuthRepository.existsBySub(sub);
    }

    public OAuth findBySub(final String sub) {
        return oAuthRepository.findBySub(sub)
                .orElseThrow(() -> new OAuthNotFoundException(AuthErrorCode.OAUTH_NOT_FOUND));
    }
}
