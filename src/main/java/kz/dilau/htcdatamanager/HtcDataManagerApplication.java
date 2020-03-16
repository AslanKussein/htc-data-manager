package kz.dilau.htcdatamanager;

import kz.dilau.htcdatamanager.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class HtcDataManagerApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(HtcDataManagerApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(HtcDataManagerApplication.class);
    }
}
