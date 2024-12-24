package com.drinkhere.apiauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.drinkhere")
public class ApiAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiAuthApplication.class, args);
    }

}
