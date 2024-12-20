package com.drinkhere.common.exception.token;

import com.drinkhere.common.exception.BaseErrorCode;
import com.drinkhere.common.exception.CustomException;

public class TokenException extends CustomException {

    public TokenException(BaseErrorCode error) {
        super(error);
    }
}
