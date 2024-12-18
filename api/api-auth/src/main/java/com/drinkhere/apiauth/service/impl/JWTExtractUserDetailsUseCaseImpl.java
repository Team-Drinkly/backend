package com.drinkhere.apiauth.service.impl;

import com.drinkhere.apiauth.service.JWTExtractUserDetailsUseCase;
import com.drinkhere.common.annotation.ApplicationService;
import com.drinkhere.domainrds.auth.jwt.JWTProvider;
import com.drinkhere.domainrds.auth.jwt.TokenType;
import lombok.RequiredArgsConstructor;

@ApplicationService
@RequiredArgsConstructor
public class JWTExtractUserDetailsUseCaseImpl implements JWTExtractUserDetailsUseCase<Long> {

    private final JWTProvider jwtProvider;

    @Override
    public Long extract(final String token) {
        return jwtProvider.extractUserClaimsFromToken(token, TokenType.ACCESS_TOKEN)
                .getUserId();
    }
}
