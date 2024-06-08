package com.atibo.backendspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
@EntityScan
public class BackendSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendSpringApplication.class, args);
	}

}
