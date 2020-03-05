package kz.dilau.htcdatamanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class HtcDataManagerApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(HtcDataManagerApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(HtcDataManagerApplication.class);
    }
}
