package com.masterswork.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

// TODO:
//  - setup test data migration with liquibase contexts
//	- add proper exception handling
//  - regenerate database migration check for typos
@ConfigurationPropertiesScan("com.masterswork.account.config")
@SpringBootApplication
public class AuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

}
