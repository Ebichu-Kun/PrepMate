package com.strawHat.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Entry point for the PrepMate Spring Boot backend application. */
@SpringBootApplication
public class BackendApplication {

	/** Boots the Spring application context. */
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
}
