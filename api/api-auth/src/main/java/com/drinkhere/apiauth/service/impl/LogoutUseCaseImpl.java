package com.drinkhere.apiauth.service.impl;

import com.drinkhere.apiauth.service.LogoutUseCase;
import com.drinkhere.apiauth.utils.TokenExtractUtils;
import com.drinkhere.common.annotation.ApplicationService;
import com.drinkhere.domainrds.auth.jwt.JWTProvider;
import com.drinkhere.domainrds.auth.jwt.TokenType;
import com.drinkhere.domainrds.auth.service.TokenDeleteService;
import com.drinkhere.infraredis.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationService
@RequiredArgsConstructor
public class LogoutUseCaseImpl implements LogoutUseCase {

    private final TokenDeleteService tokenDeleteService;
    private final RedisUtil redisUtil;
    private final JWTProvider jwtProvider;

    @Override
    public void logoutAccessUser(String refreshTokenHeader) {
        final String refreshToken = TokenExtractUtils.extractToken(refreshTokenHeader);

        // JWTProvider를 통해 사용자 ID 추출
        final Long userId = jwtProvider.extractUserIdFromToken(refreshToken);

        // Redis Key 생성
        final String key = String.format("TOKEN:%d:%s", userId, TokenType.REFRESH_TOKEN.name());

        // Redis에서 해당 토큰 삭제
        Object storedValue = redisUtil.get(key);
        if (refreshToken.equals(storedValue)) {
            tokenDeleteService.deleteToken(key);
            log.info("User logged out. Token deleted from Redis: {}", key);
        } else {
            log.warn("Token not found in Redis or mismatch: {}", key);
        }
    }
}
