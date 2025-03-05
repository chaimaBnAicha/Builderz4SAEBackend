package com.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Autoriser les requêtes CORS pour toutes les routes sous /api
                .allowedOrigins("http://localhost:4200") // Autoriser uniquement localhost:4200
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Méthodes autorisées
                .allowedHeaders("*") // Autoriser tous les en-têtes
                .allowCredentials(true); // Autoriser les cookies
    }
}