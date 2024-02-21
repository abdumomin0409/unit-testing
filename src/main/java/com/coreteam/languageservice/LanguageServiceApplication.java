package com.coreteam.languageservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class LanguageServiceApplication {
    public static void main(String[] args) {
        System.setProperty("user.timezone", "Asia/Tashkent");
        SpringApplication.run(LanguageServiceApplication.class, args);
    }

}
