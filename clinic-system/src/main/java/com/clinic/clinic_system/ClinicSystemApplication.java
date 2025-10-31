package com.clinic.clinic_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClinicSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClinicSystemApplication.class, args);
		System.out.println("\n Clinic System Started Successfully...");
		System.out.println("➡ Swagger UI: http://localhost:8080/swagger-ui.html");
		System.out.println("➡ Home Page:  http://localhost:8080/");
	}
}
