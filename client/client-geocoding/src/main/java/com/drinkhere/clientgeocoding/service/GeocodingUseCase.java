package com.drinkhere.clientgeocoding.service;

import com.drinkhere.clientgeocoding.dto.CoordinatesResponse;
import com.drinkhere.clientgeocoding.webclient.GeocodingClient;
import com.drinkhere.clientgeocoding.webclient.dto.GeocodingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeocodingUseCase {
    private final GeocodingClient geocodingClient;

    public CoordinatesResponse getCoordinates(String address) {
        GeocodingResponse geocodingResponse = geocodingClient.getCoordinates(address);
        return CoordinatesResponse.from(geocodingResponse);
    }
}
