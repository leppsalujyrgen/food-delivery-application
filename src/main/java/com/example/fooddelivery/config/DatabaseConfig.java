package com.example.fooddelivery.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.fooddelivery.repository")
public class DatabaseConfig {
    // Database configuration, if needed
}
