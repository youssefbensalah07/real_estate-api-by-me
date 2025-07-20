package com.youssef.real_estate_api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "Student API",
				version = "1.0",
				description = "API documentation for managing students"
		)
)
@SpringBootApplication
public class RealEstateApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealEstateApiApplication.class, args);
	}

}
