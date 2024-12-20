package com.drinkhere.domainrds.auth.service.impl;

import com.drinkhere.common.annotation.DomainService;
import com.drinkhere.common.exception.oauth.AuthErrorCode;
import com.drinkhere.common.exception.oauth.NotExistTokenException;
import com.drinkhere.domainrds.auth.jwt.TokenType;
import com.drinkhere.domainrds.auth.service.TokenQueryService;
import com.drinkhere.infraredis.util.RedisUtil;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class TokenQueryServiceImpl implements TokenQueryService {

    private final RedisUtil redisUtil;

    @Override
    public String findTokenByValue(String value, TokenType tokenType) {
        return findToken(value, tokenType);
    }

    private String findToken(final String value, final TokenType tokenType) {
        // Redis key 생성
        String key = String.format("TOKEN:%s:%s", value, tokenType.name());

        // Redis에서 토큰 조회
        String token = (String) redisUtil.get(key);
        if (token == null) {
            throw new NotExistTokenException(AuthErrorCode.NOT_EXIST_TOKEN);
        }
        return token;
    }
}
