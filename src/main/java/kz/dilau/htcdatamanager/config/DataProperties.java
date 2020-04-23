package kz.dilau.htcdatamanager.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@RefreshScope
@Component
@ConfigurationProperties(prefix = "data")
@Data
public class DataProperties {
    private String keycloakBaseUrl;
    private String keycloakApiUrl;
    private String keycloakRoleManagerClient;
    private String keycloakRoleManagerUrl;
    private String keycloakRoleManagerLogin;
    private String keycloakRoleManagerPassword;
}
