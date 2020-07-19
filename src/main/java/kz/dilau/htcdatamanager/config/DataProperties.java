package kz.dilau.htcdatamanager.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@RefreshScope
@Component
@ConfigurationProperties(prefix = "data")
@Data
public class DataProperties {
    private String keycloakBaseUrl;
    private String keycloakApiUrl;
    private String keycloakRoleManagerUrl;
    private String keycloakUserManagerClient;
    private String keycloakUserManagerUrl;
    private String keycloakUserManagerLogin;
    private String keycloakUserManagerPassword;
    private String keycloakFileManagerUrl;
    private int maxApplicationCountForOneRealProperty;
    private BigDecimal commissionForHouse;
    private List<CommissionRange> commissionRangeList;
    private int maxEventDemoPerDay;
}
