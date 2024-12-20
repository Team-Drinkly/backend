package com.drinkhere.domainrds.auth.service;

import com.drinkhere.common.annotation.DomainService;
import com.drinkhere.common.exception.oauth.AuthErrorCode;
import com.drinkhere.common.exception.oauth.NotExistTokenException;
import com.drinkhere.domainrds.auth.jwt.TokenType;
import com.drinkhere.infraredis.util.RedisUtil;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class TokenValidateService {
    private final RedisUtil redisUtil;

    public void validateIsExistToken(final String token, final TokenType tokenType, final String sub) {
        // Redis에서 저장된 key 형식에 맞게 조회
        String key = String.format("TOKEN:%s:%s", sub, tokenType.name());

        // Redis에서 해당 토큰이 존재하지 않으면 예외 발생
        if (!token.equals(redisUtil.get(key))) {
            throw new NotExistTokenException(AuthErrorCode.NOT_EXIST_TOKEN);
        }
    }
}
