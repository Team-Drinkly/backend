package com.drinkhere.common.exception.oauth;

import com.drinkhere.common.exception.BaseErrorCode;
import com.drinkhere.common.exception.CustomException;

public class AuthException extends CustomException {

    public AuthException(BaseErrorCode error) {
        super(error);
    }
}
