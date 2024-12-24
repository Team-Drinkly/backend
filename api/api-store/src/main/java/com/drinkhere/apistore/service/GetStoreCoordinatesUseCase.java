package com.drinkhere.apistore.service;

import com.drinkhere.clientgeocoding.dto.CoordinatesResponse;
import com.drinkhere.clientgeocoding.service.GeocodingUseCase;
import com.drinkhere.common.annotation.ApplicationService;
import lombok.RequiredArgsConstructor;

@ApplicationService
@RequiredArgsConstructor
public class GetStoreCoordinatesUseCase {
    private final GeocodingUseCase geocodingUseCase;

    public CoordinatesResponse getStoreGeocoding(String address) {
        return geocodingUseCase.getCoordinates(address);
    }

}
