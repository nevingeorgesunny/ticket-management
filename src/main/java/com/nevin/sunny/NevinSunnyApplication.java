package com.nevin.sunny;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.nevin.sunny.repositories")

public class NevinSunnyApplication {

	public static void main(String[] args) {
		SpringApplication.run(NevinSunnyApplication.class, args);
	}

}
