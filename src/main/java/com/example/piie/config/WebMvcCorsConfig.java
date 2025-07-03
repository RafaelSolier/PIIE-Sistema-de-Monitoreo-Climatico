package com.example.piie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcCorsConfig {

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")                    // Rutas a las que aplica CORS                   // Rutas a las que aplica CORS
                        .allowedOrigins("*")       // Orígenes permitidos
                        .allowedMethods("GET", "POST", "PUT", "DELETE","PATCH","OPTIONS")
                        .allowedHeaders("Content-Type", "Authorization")
                        .maxAge(3600);
            }
        };
    }
}