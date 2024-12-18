package com.drinkhere.domainrds.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthSaveService {
    private final OAuthRepository oAuthRepository;

    public void save(final OAuth oAuth) {
        oAuthRepository.save(oAuth);
    }
}
