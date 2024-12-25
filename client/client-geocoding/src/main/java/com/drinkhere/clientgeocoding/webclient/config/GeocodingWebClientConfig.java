package com.drinkhere.clientgeocoding.webclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GeocodingWebClientConfig {
    @Bean
    public WebClient geocodingWebClient(WebClient.Builder builder) {
        return builder.baseUrl("https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode").build();
    }
}