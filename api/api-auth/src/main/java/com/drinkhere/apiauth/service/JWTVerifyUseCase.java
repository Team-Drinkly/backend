package com.drinkhere.apiauth.service;

public interface JWTVerifyUseCase {
    void validateToken(final String token);
}
