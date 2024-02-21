package com.coreteam.languageservice.config;/*
 User : abdumomin
 Date :8/2/23

*/


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.validation.constraints.NotNull;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static com.coreteam.languageservice.controller.BaseURL.*;

@Configuration
public class OpenApiConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NotNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*");
            }
        };
    }

    @Bean
    public GroupedOpenApi allOpenApi() {
        return GroupedOpenApi.builder()
                .group("all")
                .pathsToMatch(BASE_URL + "/**")
                .build();
    }

    @Bean
    public GroupedOpenApi statusOpenApi() {
        return GroupedOpenApi.builder()
                .group("status")
                .pathsToMatch(STATUS_URL + "/**")
                .build();
    }

    @Bean
    public GroupedOpenApi modulOpenApi() {
        return GroupedOpenApi.builder()
                .group("modul")
                .pathsToMatch(MODUL_URL + "/**")
                .build();
    }

    @Bean
    public GroupedOpenApi foreignLangOpenApi() {
        return GroupedOpenApi.builder()
                .group("foreign-lang")
                .pathsToMatch(FOREIGN_LANG_URL + "/**")
                .build();
    }

    @Bean
    public GroupedOpenApi langOpenApi() {
        return GroupedOpenApi.builder()
                .group("language")
                .pathsToMatch(LANGUAGE_URL + "/**")
                .build();
    }

    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(" Language service API")
                        .description("API for language service")
                        .version("0.0.1")
                        .contact(new Contact()
                                .name("Sandbox")
                                .email("sandbox@gmail.com")
                                .url("https://github.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org"))
                        .termsOfService("http://swagger.io/terms/"))
                .servers(List.of(
                        new Server().url("http://backend.coreteam.uz:8093").description("Server url"),
                        new Server().url("http://localhost:8093").description("My local server"))
                );
    }


}
