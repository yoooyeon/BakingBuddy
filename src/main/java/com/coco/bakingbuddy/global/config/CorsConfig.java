package com.coco.bakingbuddy.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static java.util.Arrays.asList;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(
                asList("http://localhost:3001"
                        , "http://localhost:3002"
                        , "http://localhost:3000"
                        , "http://localhost:8080"
                        , "http://127.0.0.1:3001"
                        , "https://baking-buddy-frontend-rg7h.vercel.app"
                ));
        configuration.setAllowedMethods(asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
