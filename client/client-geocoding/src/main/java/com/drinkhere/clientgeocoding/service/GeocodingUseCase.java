package com.drinkhere.clientgeocoding.service;

import com.drinkhere.clientgeocoding.dto.Coordinates;
import com.drinkhere.clientgeocoding.webclient.GeocodingClient;
import com.drinkhere.clientgeocoding.webclient.dto.GeocodingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeocodingUseCase {
    private final GeocodingClient geocodingClient;

    public Coordinates getCoordinates(String address) {
        GeocodingResponse geocodingResponse = geocodingClient.getCoordinates(address);
        return Coordinates.from(geocodingResponse);
    }
}
