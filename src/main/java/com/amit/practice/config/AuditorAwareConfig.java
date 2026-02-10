package com.amit.practice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
public class AuditorAwareConfig {

    /**
     * Provides the current auditor (user) for createdBy/updatedBy audit fields.
     * Returns "system" when no authenticated user is available.
     * Replace with SecurityContext-based implementation when using Spring Security.
     */
    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of("system");
    }
}
