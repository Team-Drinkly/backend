package com.drinkhere.domainrds.auth.service;

import com.drinkhere.domainrds.auth.jwt.TokenType;

public interface TokenQueryService {

    String findTokenByValue(final String value, final TokenType tokenType);
}