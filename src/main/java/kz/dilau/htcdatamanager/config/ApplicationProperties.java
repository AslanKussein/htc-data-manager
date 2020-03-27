package kz.dilau.htcdatamanager.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
//@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
//    private String dbTableNamePrefix;
}
