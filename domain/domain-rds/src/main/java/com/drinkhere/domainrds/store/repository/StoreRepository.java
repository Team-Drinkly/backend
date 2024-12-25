package com.drinkhere.domainrds.store.repository;

import com.drinkhere.domainrds.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
