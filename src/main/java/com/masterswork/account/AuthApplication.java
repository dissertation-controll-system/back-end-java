package com.masterswork.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

// Must have:
// TODO: setup test data migration with liquibase contexts
// TODO: regenerate database migration check for typos
// TODO: implement RESTfull/like api for existing entities
// TODO: add pagination and filtering
// TODO: use swagger annotations for proper docs

// Testing:
// TODO: add unit tests
// TODO: persistence tests
// TODO: IT tests
// TODO: slice tests

// Lower priority:
// TODO: configure profiles
// TODO: disable swagger-ui, api-docs in production profiles
// TODO: provide README
// TODO: add caching
// TODO: add healthcheck metrics
// TODO: implement use of rsa key pair encoding algorithm for JWT

// Optional:
// TODO: add ability to recover password via email
// TODO: implement user email verification... via email :)
// TODO: find a way to configure and deploy with k8s
@ConfigurationPropertiesScan("com.masterswork.account.config")
@SpringBootApplication
public class AuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

}
