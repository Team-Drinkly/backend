package com.drinkhere.domainrds.auth.service;

import com.drinkhere.domainrds.auth.jwt.TokenType;

public interface TokenSaveService {

    void saveToken(final String token, final TokenType tokenType, final Long userId);
}
