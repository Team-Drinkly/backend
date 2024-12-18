package com.drinkhere.domainrds.auth.service.impl;

import com.drinkhere.common.annotation.DomainService;
import com.drinkhere.common.exception.oauth.AuthErrorCode;
import com.drinkhere.common.exception.oauth.NotExistTokenException;
import com.drinkhere.domainrds.auth.jwt.TokenType;
import com.drinkhere.domainrds.auth.service.TokenQueryService;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class TokenQueryServiceImpl implements TokenQueryService {

    private final TokenRepository tokenRepository;

    @Override
    public Token findTokenByValue(String value, TokenType tokenType) {
        return findToken(value, tokenType);
    }

    private Token findToken(final String value, final TokenType tokenType) {
        return tokenRepository.findByValueAndTokenType(value, tokenType)
                .orElseThrow(() -> new NotExistTokenException(AuthErrorCode.NOT_EXIST_TOKEN));
    }


}
