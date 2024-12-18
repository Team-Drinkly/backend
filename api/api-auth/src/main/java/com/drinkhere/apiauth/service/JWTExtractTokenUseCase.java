package com.drinkhere.apiauth.service;

public interface JWTExtractTokenUseCase {
    String extractToken(final String tokenHeader);
}
