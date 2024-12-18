package com.drinkhere.common.exception.token;

import com.pawith.commonmodule.exception.BusinessException;

public class TokenException extends BusinessException {
    public TokenException(Error error) {
        super(error);
    }
}
