package com.drinkhere.apiauth.service.impl;

import com.drinkhere.apiauth.service.JWTExtractTokenUseCase;
import com.drinkhere.apiauth.utils.TokenExtractUtils;
import com.drinkhere.common.annotation.ApplicationService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationService
public class JWTExtractTokenUseCaseImpl implements JWTExtractTokenUseCase {
    @Override
    public String extractToken(final String tokenHeader) {
        return TokenExtractUtils.extractToken(tokenHeader);
    }
}
