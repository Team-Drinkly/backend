package com.drinkhere.clientgeocoding.dto;

import com.drinkhere.clientgeocoding.webclient.dto.GeocodingResponse;

public record CoordinatesResponse(
        String x, // 위도
        String y // 경도
) {
    public static CoordinatesResponse from(GeocodingResponse geocodingResponse) {
        GeocodingResponse.Address address = geocodingResponse.addresses().get(0);
        return new CoordinatesResponse(
                address.x(),
                address.y()
        );
    }
}
