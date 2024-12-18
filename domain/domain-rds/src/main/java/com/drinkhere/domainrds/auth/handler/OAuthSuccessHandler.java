package com.drinkhere.domainrds.auth.handler;

import com.drinkhere.domainrds.auth.entity.OAuth;
import com.drinkhere.domainrds.auth.handler.request.OAuthSuccessEvent;
import com.drinkhere.domainrds.auth.service.OAuthQueryService;
import com.drinkhere.domainrds.auth.service.OAuthSaveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler {
    private final OAuthQueryService oAuthQueryService;
    private final OAuthSaveService oAuthSaveService;
    private final UserQueryService userQueryService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @EventListener(OAuthSuccessEvent.class)
    @Transactional
    /**
     * TODO: 리팩터링,리팩터링,리팩터링,리팩터링,리팩터링,리팩터링,리팩터링,리팩터링,리팩터링,리팩터링,리팩터링
     */
    public void handle(OAuthSuccessEvent oAuthSuccessEvent) {
        if (oAuthQueryService.existBySub(oAuthSuccessEvent.sub())) {
            final OAuth oAuth = oAuthQueryService.findBySub(oAuthSuccessEvent.sub());
            final User user = userQueryService.findById(oAuth.getUserId());
            user.updateEmail(oAuthSuccessEvent.email());
        } else {
            if (userQueryService.checkEmailAlreadyExist(oAuthSuccessEvent.email())) {
                final User user = userQueryService.findByEmail(oAuthSuccessEvent.email());
                if (oAuthQueryService.existByUserId(user.getId()) || user.isNotMatchingProvider(oAuthSuccessEvent.provider())) {
                    throw new OAuthException(AuthError.INVALID_OAUTH_REQUEST);
                }
                saveOAuth(oAuthSuccessEvent, user);
            } else {
                applicationEventPublisher.publishEvent(UserSignUpEvent.of(
                        oAuthSuccessEvent.username(),
                        oAuthSuccessEvent.email()
                ));
                final User user = userQueryService.findByEmail(oAuthSuccessEvent.email());
                saveOAuth(oAuthSuccessEvent, user);
            }
        }
    }

    private void saveOAuth(OAuthSuccessEvent oAuthSuccessEvent, User user) {
        OAuth oAuth = OAuth.of(
                oAuthSuccessEvent.provider(),
                oAuthSuccessEvent.sub(),
                user.getId()
        );
        oAuthSaveService.save(oAuth);
    }
}
