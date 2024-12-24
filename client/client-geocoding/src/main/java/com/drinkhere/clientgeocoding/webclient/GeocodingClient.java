package com.drinkhere.clientgeocoding.webclient;

import com.drinkhere.clientgeocoding.webclient.config.GeocodingProperties;
import com.drinkhere.clientgeocoding.webclient.dto.GeocodingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class GeocodingClient {
    private final WebClient geocodingWebClient;
    private final GeocodingProperties geocodingProperties;

    public GeocodingResponse getCoordinates(String address) {
        return geocodingWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", address)
                        .build())
                .header("x-ncp-apigw-api-key-id", geocodingProperties.getClientId())
                .header("x-ncp-apigw-api-key", geocodingProperties.getClientSecret())
                .header("Accept", "application/json")
                .retrieve()
                .bodyToMono(GeocodingResponse.class)
                .block(); // 응답을 GeocodingResponse로 매핑
    }
}
