package org.dice.webservice;


import org.dice.qa.QASystem;
import org.dice.qa.impl.ExampleQASystem;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebApplication {
	
	@Bean
	public QASystem createSystem() {
		/*
		 * This is an Example QA System providing a static response. 
		 * Implement your System as a QASystem and create it here
		 * 
		 * CREATE YOUR SYSTEM HERE 
		 */
		return new ExampleQASystem();
	}
	
	public static void main(final String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}
}
