package com.drinkhere.common.exception.oauth;

import com.drinkhere.common.exception.BaseErrorCode;
import com.drinkhere.common.exception.CustomException;

public class OAuthException extends CustomException {

    public OAuthException(BaseErrorCode error) {
        super(error);
    }
}
