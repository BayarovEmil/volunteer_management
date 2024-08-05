package com.cognito.volunteer_managment_system;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class VolunteerManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(VolunteerManagementSystemApplication.class, args);
	}

}
