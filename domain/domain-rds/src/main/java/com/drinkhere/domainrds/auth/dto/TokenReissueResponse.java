package com.drinkhere.domainrds.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class TokenReissueResponse {
    private String accessToken;
    private String refreshToken;
}
