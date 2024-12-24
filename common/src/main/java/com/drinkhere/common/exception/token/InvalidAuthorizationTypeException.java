package com.drinkhere.common.exception.token;

import com.drinkhere.common.exception.BaseErrorCode;
import com.drinkhere.common.exception.CustomException;

public class InvalidAuthorizationTypeException extends CustomException {

    public InvalidAuthorizationTypeException(BaseErrorCode error) {
        super(error);
    }
}
