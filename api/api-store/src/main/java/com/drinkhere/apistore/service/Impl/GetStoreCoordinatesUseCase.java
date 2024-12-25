package com.drinkhere.apistore.service.Impl;

import com.drinkhere.clientgeocoding.dto.Coordinates;
import com.drinkhere.clientgeocoding.service.GeocodingUseCase;
import com.drinkhere.common.annotation.ApplicationService;
import lombok.RequiredArgsConstructor;

@ApplicationService
@RequiredArgsConstructor
public class GetStoreCoordinatesUseCase {
    private final GeocodingUseCase geocodingUseCase;

    public Coordinates convertAddressToCoordinates(String address) {
        return geocodingUseCase.getCoordinates(address);
    }

}
