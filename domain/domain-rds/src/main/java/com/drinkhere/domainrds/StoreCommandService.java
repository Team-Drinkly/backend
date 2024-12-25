package com.drinkhere.domainrds;

import com.drinkhere.domainrds.store.entity.Store;
import com.drinkhere.domainrds.store.repository.StoreImageRepository;
import com.drinkhere.domainrds.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreCommandService {
    private final StoreRepository storeRepository;
    private final StoreImageRepository storeImageRepository;

    public void saveStore(Store store) {
        storeRepository.save(store);
    }
}
