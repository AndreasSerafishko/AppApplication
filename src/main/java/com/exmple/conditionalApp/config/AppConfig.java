package com.exmple.conditionalApp.config;

import com.exmple.conditionalApp.profile.DevProfile;
import com.exmple.conditionalApp.profile.ProductionProfile;
import com.exmple.conditionalApp.profile.SystemProfile;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    @ConditionalOnProperty(prefix = "netology.profile", name = "dev", havingValue = "true", matchIfMissing = false)
    public SystemProfile devProfile() {
        System.out.println("Creating DevProfile bean"); // Для отладки
        return new DevProfile();
    }

    @Bean
    @ConditionalOnProperty(prefix = "netology.profile", name = "dev", havingValue = "false", matchIfMissing = true)
    public SystemProfile prodProfile() {
        System.out.println("Creating ProductionProfile bean"); // Для отладки
        return new ProductionProfile();
    }

}

