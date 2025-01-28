package com.drinkhere.clientgeocoding.webclient.dto;

import java.util.List;

public record GeocodingResponse(
        String status,
        Meta meta,
        List<Address> addresses,
        String errorMessage
) {

    public record Meta(int totalCount, int page, int count) {}

    public record Address(
            String roadAddress,
            String jibunAddress,
            String englishAddress,
            List<AddressElement> addressElements,
            String x, // 경도 (longitude)
            String y, // 위도 (latitude)
            double distance
    ) {}

    public record AddressElement(
            List<String> types,
            String longName,
            String shortName,
            String code
    ) {}
}
