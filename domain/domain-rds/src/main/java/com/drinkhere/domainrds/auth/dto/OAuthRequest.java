package com.drinkhere.domainrds.auth.dto;

import com.drinkhere.domainrds.auth.enums.Provider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuthRequest {
    private Provider provider;
    private String accessToken;
}
