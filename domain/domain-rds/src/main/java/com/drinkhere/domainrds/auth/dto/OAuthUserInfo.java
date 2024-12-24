package com.drinkhere.domainrds.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OAuthUserInfo {

    private final String sub;
    private final String email;
    private final String nickname;
}
