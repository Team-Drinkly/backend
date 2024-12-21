package com.drinkhere.common.exception.token;

import com.drinkhere.common.exception.BaseErrorCode;
import com.drinkhere.common.exception.CustomException;

public class InvalidTokenException extends CustomException {

    public InvalidTokenException(BaseErrorCode error) {
        super(error);
    }
}
