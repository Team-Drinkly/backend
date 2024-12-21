package com.drinkhere.apiauth.service.impl;

import com.drinkhere.apiauth.service.JWTExtractUserDetailsUseCase;
import com.drinkhere.common.annotation.ApplicationService;
import com.drinkhere.common.exception.oauth.AuthErrorCode;
import com.drinkhere.common.exception.token.InvalidTokenException;
import com.drinkhere.domainrds.auth.jwt.JWTProvider;
import lombok.RequiredArgsConstructor;

@ApplicationService
@RequiredArgsConstructor
public class JWTExtractUserDetailsUseCaseImpl implements JWTExtractUserDetailsUseCase<Long> {

    private final JWTProvider jwtProvider;

    @Override
    public Long extract(final String token) {
        try {
            // 토큰에서 UserId 추출
            return jwtProvider.extractUserIdFromToken(token);
        } catch (Exception e) {
            // 예외 로깅 및 사용자 친화적인 예외 반환
            throw new InvalidTokenException(AuthErrorCode.INVALID_TOKEN);
        }
    }
}
