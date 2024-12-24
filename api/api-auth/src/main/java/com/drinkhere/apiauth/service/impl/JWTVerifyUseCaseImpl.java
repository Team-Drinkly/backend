package com.drinkhere.apiauth.service.impl;

import com.drinkhere.apiauth.service.JWTVerifyUseCase;
import com.drinkhere.common.annotation.ApplicationService;
import com.drinkhere.domainrds.auth.jwt.JWTProvider;
import com.drinkhere.domainrds.auth.jwt.TokenType;
import lombok.RequiredArgsConstructor;

@ApplicationService
@RequiredArgsConstructor
public class JWTVerifyUseCaseImpl implements JWTVerifyUseCase {

    private final JWTProvider jwtProvider;

    @Override
    public void validateToken(final String token){
        jwtProvider.validateToken(token, TokenType.ACCESS_TOKEN);
    }
}
