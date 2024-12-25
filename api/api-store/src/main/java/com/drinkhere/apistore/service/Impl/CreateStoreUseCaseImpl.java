package com.drinkhere.apistore.service.Impl;

import com.drinkhere.domainrds.store.dto.CreateStoreRequest;
import com.drinkhere.apistore.service.CreateStoreUseCase;
import com.drinkhere.clientgeocoding.dto.Coordinates;
import com.drinkhere.common.annotation.ApplicationService;
import com.drinkhere.domainrds.store.entity.Store;
import lombok.RequiredArgsConstructor;

@ApplicationService
@RequiredArgsConstructor
public class CreateStoreUseCaseImpl implements CreateStoreUseCase {
    private final GetStoreCoordinatesUseCase getStoreCoordinatesUseCase;

    @Override
    public void createStore(CreateStoreRequest createStoreRequest) {

        // 1. 주소를 좌표로 변환
        String address = createStoreRequest.address();
        Coordinates coordinates = getStoreCoordinatesUseCase.convertAddressToCoordinates(address);

        // 2. Store Entity 생성
        Store store = Store.builder()
                .storeName(createStoreRequest.storeName())
                .description(createStoreRequest.description())
                .openingHour(createStoreRequest.openingHour())
                .address(address)
                .storeNo(createStoreRequest.storeNo())
                .instagram(createStoreRequest.instagram())
                .availableBeverage(createStoreRequest.availableBeverage())
                .latitude(coordinates.y())
                .longitude(coordinates.x())
                .build();

        // 3. DB 저장

    }
}

