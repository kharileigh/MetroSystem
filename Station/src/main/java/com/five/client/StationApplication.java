package com.five.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.five")
@EntityScan(basePackages = "com.five.entity")
@EnableJpaRepositories(basePackages = "com.five.persistence")
public class StationApplication {

	public static void main(String[] args) {
		SpringApplication.run(StationApplication.class, args);
	}

}
