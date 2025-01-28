package com.drinkhere.clientgeocoding.dto;

import com.drinkhere.clientgeocoding.webclient.dto.GeocodingResponse;

public record Coordinates(
        String x, // 위도
        String y // 경도
) {
    public static Coordinates from(GeocodingResponse geocodingResponse) {
        GeocodingResponse.Address address = geocodingResponse.addresses().get(0);
        return new Coordinates(
                address.x(),
                address.y()
        );
    }
}
