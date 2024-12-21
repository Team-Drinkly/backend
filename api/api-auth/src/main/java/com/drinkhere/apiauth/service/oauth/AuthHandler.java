package com.drinkhere.apiauth.service.oauth;

import com.drinkhere.domainrds.auth.dto.OAuthRequest;
import com.drinkhere.domainrds.auth.dto.OAuthUserInfo;

public interface AuthHandler{

    OAuthUserInfo handle(OAuthRequest authenticationInfo);

    default boolean isAccessible(OAuthRequest authenticationInfo){
        return false;
    }
}
