package com.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableScheduling
public class MyFirstCrudOperationApplication {

	public static void main(String[] args) {

		SpringApplication.run(MyFirstCrudOperationApplication.class, args);
		System.err.println("Welcome to developer portal. Todays date is "+ LocalDateTime.now());
	}



}
