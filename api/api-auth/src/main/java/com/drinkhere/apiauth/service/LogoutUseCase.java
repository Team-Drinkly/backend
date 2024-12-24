package com.drinkhere.apiauth.service;

public interface LogoutUseCase {

    void logoutAccessUser(String refreshTokenHeader);
}
