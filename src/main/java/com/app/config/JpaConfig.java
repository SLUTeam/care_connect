package com.app.config;

import java.util.Optional;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
@EntityScan(basePackageClasses = JpaMarker.class)
@EnableJpaRepositories(basePackageClasses = JpaMarker.class)
@EnableJpaAuditing
public class JpaConfig {

 


    @Bean
    public AuditorAware<String> springSecurityAuditorAware() {
        return new AuditorAware<String> () {
            @Override
            public Optional<String> getCurrentAuditor() {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication == null
                        || !authentication.isAuthenticated()
                        || (authentication instanceof AnonymousAuthenticationToken)
                        ) {
                  return Optional.of("anon");
                }
                // note that getUsername() actually returns the ID of the user. So the createdBy and lastmodifiedBy
                // fields will contain IDs. this is by design, because we don't want unencrypted email/mobile to show up 
                // in any table.
                return Optional.of(String.valueOf(((UserDetails) authentication.getPrincipal()).getUsername()));
            }
        };
    }

 

 

 

}