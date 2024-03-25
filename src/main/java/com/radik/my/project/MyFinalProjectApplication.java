package com.radik.my.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableWebSecurity
@EnableTransactionManagement
public class MyFinalProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyFinalProjectApplication.class, args);
	}

}
