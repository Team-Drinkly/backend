package com.drinkhere.clientnice;

import com.drinkhere.clientnice.webclient.config.NiceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ConfigurationPropertiesScan
@ComponentScan(basePackages = "com.drinkhere")
public class ClientNiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientNiceApplication.class, args);
    }

}
