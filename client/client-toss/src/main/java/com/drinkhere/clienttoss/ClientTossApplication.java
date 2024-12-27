package com.drinkhere.clienttoss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ConfigurationPropertiesScan
@ComponentScan(basePackages = "com.drinkhere")
public class ClientTossApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientTossApplication.class, args);
	}

}
