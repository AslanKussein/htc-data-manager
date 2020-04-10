package kz.dilau.htcdatamanager.config;

import kz.dilau.htcdatamanager.web.rest.vm.ApplicationType;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Optional;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToApplicationTypeEnumConverter());
    }

    private class StringToApplicationTypeEnumConverter implements Converter<String, ApplicationType> {
        @Override
        public ApplicationType convert(String s) {
            return Optional.ofNullable(ApplicationType.valueOf(s)).orElseThrow(IllegalArgumentException::new);
        }
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }
}
