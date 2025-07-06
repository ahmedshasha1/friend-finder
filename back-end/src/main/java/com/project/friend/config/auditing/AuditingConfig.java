package com.project.friend.config.auditing;

import com.project.friend.model.auth.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;


@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditingConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("principle: "+ auth.getPrincipal());
            if (auth != null && auth.isAuthenticated()) {
                User user = (User) auth.getPrincipal();
                return Optional.of(user.getEmail());
            }
            return Optional.empty();
        };
    }
}