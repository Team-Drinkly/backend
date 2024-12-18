package com.drinkhere.apiauth.service.oauth;

import com.drinkhere.common.exception.oauth.AuthErrorCode;
import com.drinkhere.common.exception.oauth.AuthException;
import com.drinkhere.domainrds.auth.consts.AuthConsts;
import com.drinkhere.domainrds.auth.dto.OAuthRequest;
import com.drinkhere.domainrds.auth.dto.OAuthResponse;
import com.drinkhere.domainrds.auth.dto.OAuthUserInfo;
import com.drinkhere.domainrds.auth.handler.request.OAuthSuccessEvent;
import com.drinkhere.domainrds.auth.jwt.JWTProvider;
import com.drinkhere.domainrds.auth.jwt.PrivateClaims;
import com.drinkhere.domainrds.auth.jwt.TokenType;
import com.drinkhere.domainrds.auth.service.OAuthQueryService;
import com.drinkhere.domainrds.auth.service.TokenSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OAuthInvoker {
    private final List<AuthHandler> authHandlerList;
    private final JWTProvider jwtProvider;
    private final TokenSaveService tokenSaveService;
    private final ApplicationEventPublisher publisher;
    private final OAuthQueryService oAuthQueryService;

    public OAuthResponse execute(OAuthRequest request) {
        final OAuthUserInfo oAuthUserInfo = attemptLogin(request);
        publishEvent(oAuthUserInfo, request);
        return generateServerAuthenticationTokens(
                PrivateClaims.UserClaims.of(oAuthQueryService.findBySub(oAuthUserInfo.getSub()).getUserId())
        );
    }

    private void publishEvent(OAuthUserInfo oAuthUserInfo, OAuthRequest request) {
        publisher.publishEvent(OAuthSuccessEvent.of(
                oAuthUserInfo.getNickname(),
                oAuthUserInfo.getEmail(),
                request.getProvider(),
                oAuthUserInfo.getSub()
        ));
    }

    private OAuthUserInfo attemptLogin(OAuthRequest request) {
        for (AuthHandler authHandler : authHandlerList) {
            if (authHandler.isAccessible(request)) {
                return authHandler.handle(request);
            }
        }
        throw new AuthException(AuthErrorCode.OAUTH_FAIL);
    }

    private OAuthResponse generateServerAuthenticationTokens(PrivateClaims.UserClaims userClaims) {
        final JWTProvider.Token token = jwtProvider.generateToken(userClaims);
        tokenSaveService.saveToken(token.refreshToken(), TokenType.REFRESH_TOKEN, userClaims.getUserId());
        final String accessToken = AuthConsts.AUTHENTICATION_TYPE_PREFIX + token.accessToken();
        final String refreshToken = AuthConsts.AUTHENTICATION_TYPE_PREFIX + token.refreshToken();
        return OAuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
