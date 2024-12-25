package com.drinkhere.domainrds.store.repository;

import com.drinkhere.domainrds.store.entity.StoreImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreImageRepository extends JpaRepository<StoreImage, Long> {
}
