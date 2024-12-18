package com.drinkhere.domainrds.auth.service.impl;

import com.drinkhere.common.annotation.DomainService;
import com.drinkhere.domainrds.auth.service.TokenDeleteService;
import com.drinkhere.infraredis.util.RedisUtil;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DomainService
@RequiredArgsConstructor
public class TokenDeleteServiceImpl implements TokenDeleteService {

    private final RedisUtil redisUtil;

    @Override
    public void deleteToken(final String key) {
        redisUtil.delete(key);
    }

    @Override
    public void deleteAllTokens(final List<String> keys) {
        keys.forEach(redisUtil::delete);
    }

    @Override
    public void deleteTokenByValue(final String value, final Long userId) {
        // Redis key 생성 (예: "TOKEN:userId:REFRESH_TOKEN")
        String key = String.format("TOKEN:%d:%s", userId, "REFRESH_TOKEN");

        // 값이 일치하면 삭제
        Object storedValue = redisUtil.get(key);
        if (value.equals(storedValue)) {
            redisUtil.delete(key);
        }
    }
}