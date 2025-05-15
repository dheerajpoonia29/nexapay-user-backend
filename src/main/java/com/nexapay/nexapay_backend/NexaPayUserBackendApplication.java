package com.nexapay.nexapay_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.nexapay.model")
@EnableJpaRepositories(basePackages = "com.nexapay.nexapay_backend.dao")
public class NexaPayUserBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(NexaPayUserBackendApplication.class, args);
	}

}
