package com.smarttask.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // allow all paths
                .allowedOriginPatterns("*") // allow all origins
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // allow all common methods
                .allowedHeaders("*") // allow all headers
                .allowCredentials(true); // allow cookies/auth headers
    }
}
