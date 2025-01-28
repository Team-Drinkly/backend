package com.drinkhere.clientgeocoding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ConfigurationPropertiesScan
@ComponentScan(basePackages = "com.drinkhere")
public class ClientGeocodingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientGeocodingApplication.class, args);
    }

}
