package com.isslng.banking.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication
public class TransactionProcessorApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(TransactionProcessorApplication.class, args);
	}
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TransactionProcessorApplication.class);
    }

}
