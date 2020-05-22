package kz.dilau.htcdatamanager.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@RefreshScope
@Component
@ConfigurationProperties(prefix = "postmapper")
@Data
public class KazPostMapperProperties {
    private int country;
    private int city;
    private int district;
    private int street;
}
