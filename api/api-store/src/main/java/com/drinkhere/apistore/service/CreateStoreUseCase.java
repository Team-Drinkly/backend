package com.drinkhere.apistore.service;

import com.drinkhere.domainrds.store.dto.CreateStoreRequest;

public interface CreateStoreUseCase {
    void createStore(CreateStoreRequest createStoreRequest);
}
