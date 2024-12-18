package com.drinkhere.common.exception.token;

import com.drinkhere.common.exception.BaseErrorCode;
import com.drinkhere.common.exception.CustomException;

public class ExpiredTokenException extends CustomException {

    public ExpiredTokenException(BaseErrorCode error) {
        super(error);
    }
}
