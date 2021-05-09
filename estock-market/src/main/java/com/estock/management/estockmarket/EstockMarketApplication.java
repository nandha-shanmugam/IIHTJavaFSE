package com.estock.management.estockmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;

@EnableAutoConfiguration
@SpringBootApplication
public class EstockMarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstockMarketApplication.class, args);
	}
	
	@Bean
	public ObjectMapper objectMapperInit() {
		return new ObjectMapper();
	}

}
