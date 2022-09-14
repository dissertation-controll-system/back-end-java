package com.masterswork.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

// TODO: setup test data migration with liquibase contexts
// TODO: add proper exception handling
// TODO: regenerate database migration check for typos
// TODO: extend crud for all entities
// TODO: use swagger annotations for proper docs
// TODO: add healthcheck metrics
// TODO: add unit tests
// TODO: persistence tests
// TODO: IT tests
// TODO: slice tests
// TODO: provide README

// TODO: find a way to configure and deploy with k8s
@ConfigurationPropertiesScan("com.masterswork.account.config")
@SpringBootApplication
public class AuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

}
