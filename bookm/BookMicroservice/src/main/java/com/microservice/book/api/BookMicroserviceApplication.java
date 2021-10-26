package com.microservice.book.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableHystrixDashboard
@EntityScan(basePackages = "com.microservice.commons.entity")
public class BookMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookMicroserviceApplication.class, args);
	}

}
