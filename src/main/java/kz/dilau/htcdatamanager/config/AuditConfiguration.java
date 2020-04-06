package kz.dilau.htcdatamanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

import static kz.dilau.htcdatamanager.config.Constants.SYSTEM_ACCOUNT;

@Configuration
@EnableJpaAuditing
public class AuditConfiguration {
    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of(SYSTEM_ACCOUNT);//todo to do
    }
}
