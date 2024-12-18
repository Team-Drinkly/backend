package com.drinkhere.domainrds.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.drinkhere")
public class FeignClientConfig {
}
