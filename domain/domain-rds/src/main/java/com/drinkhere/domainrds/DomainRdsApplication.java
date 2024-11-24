package com.drinkhere.domainrds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.drinkhere")
public class DomainRdsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DomainRdsApplication.class, args);
	}

}
