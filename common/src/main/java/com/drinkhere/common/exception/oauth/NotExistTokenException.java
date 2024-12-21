package com.drinkhere.common.exception.oauth;

import com.drinkhere.common.exception.BaseErrorCode;
import com.drinkhere.common.exception.CustomException;

public class NotExistTokenException extends CustomException {

    public NotExistTokenException(BaseErrorCode error) {
        super(error);
    }
}
