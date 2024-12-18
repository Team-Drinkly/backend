package com.drinkhere.apiauth.service;

public interface JWTExtractUserDetailsUseCase<T> {
    T extract(final String token);
}
