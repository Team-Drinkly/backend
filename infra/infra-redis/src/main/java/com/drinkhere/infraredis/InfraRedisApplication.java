package com.drinkhere.infraredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.drinkhere")
public class InfraRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(InfraRedisApplication.class, args);
    }

}
