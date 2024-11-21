package com.example.back.teamate;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableJpaAuditing
public class TeamateApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamateApplication.class, args);
	}

}
