package com.example.today;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class TodaySpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodaySpringApplication.class, args);
	}

}
