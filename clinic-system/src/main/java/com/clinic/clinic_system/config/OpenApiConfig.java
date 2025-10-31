package com.clinic.clinic_system.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI clinicSystemOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Clinic Management System API")
                        .description("REST API documentation for Healthcare Appointment & Patient Record System")
                        .version("1.0.0")
                );
    }
}
