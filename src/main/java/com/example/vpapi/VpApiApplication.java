package com.example.vpapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class VpApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(VpApiApplication.class, args);
	}

}
