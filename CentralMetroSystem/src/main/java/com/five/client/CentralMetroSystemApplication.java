package com.five.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = "com.five")
@EntityScan(basePackages = "com.five.entity")
@EnableJpaRepositories(basePackages = "com.five.model.persistence")
public class CentralMetroSystemApplication {

	public static void main(String[] args) {
            
		SpringApplication.run(CentralMetroSystemApplication.class, args);
	}
        
        // REST TEMPLATE - makes calls to User & Station REST API
        @Bean
        public RestTemplate getTemplate() {
            return new RestTemplate();
        }

}
