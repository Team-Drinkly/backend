package com.drinkhere.domainrds.auth.service;

import com.drinkhere.domainrds.auth.entity.OAuth;
import com.drinkhere.domainrds.auth.repository.OAuthRepository;
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
