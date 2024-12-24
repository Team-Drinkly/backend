package com.drinkhere.common.exception.clientnice;

import com.drinkhere.common.exception.BaseErrorCode;
import com.drinkhere.common.exception.CustomException;

public class NiceException extends CustomException {
    public NiceException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
