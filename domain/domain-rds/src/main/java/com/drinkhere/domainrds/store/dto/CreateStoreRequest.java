package com.drinkhere.domainrds.store.dto;

public record CreateStoreRequest(
        String storeName,
        String description,
        String openingHour,
        String address,
        String storeNo,
        String instagram,
        String availableBeverage
) {
}
