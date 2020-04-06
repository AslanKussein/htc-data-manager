package kz.dilau.htcdatamanager;

//import kz.dilau.htcdatamanager.config.ApplicationProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//@EnableDiscoveryClient
//@EnableConfigurationProperties(ApplicationProperties.class)
public class HtcDataManagerApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
//        new SpringApplicationBuilder(HtcDataManagerApplication.class)
//                .web(true).run(args);
        SpringApplication.run(HtcDataManagerApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(HtcDataManagerApplication.class);
    }
}
