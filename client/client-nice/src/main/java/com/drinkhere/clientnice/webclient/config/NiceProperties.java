package com.drinkhere.clientnice.webclient.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ConfigurationProperties(prefix = "nice-api")
public class NiceProperties {
    private String organizationToken;
    private String clientId;
    private String clientSecret;
    private String productId;
    private String callbackUrl;
}