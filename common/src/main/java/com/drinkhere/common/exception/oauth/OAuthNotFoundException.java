package com.drinkhere.common.exception.oauth;

import com.drinkhere.common.exception.BaseErrorCode;

public class OAuthNotFoundException extends OAuthException {
    public OAuthNotFoundException(BaseErrorCode error) {
        super(error);
    }
}
