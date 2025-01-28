package com.drinkhere.clientgeocoding.webclient.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter @Setter
@ConfigurationProperties(prefix = "naver-cloud")
public class GeocodingProperties {
    private String clientId;
    private String clientSecret;
}