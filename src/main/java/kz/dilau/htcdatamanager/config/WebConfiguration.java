package kz.dilau.htcdatamanager.config;

import kz.dilau.htcdatamanager.service.dictionary.Dictionary;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToDictionaryEnumConverter());
    }

    private class StringToDictionaryEnumConverter implements Converter<String, Dictionary> {
        @Override
        public Dictionary convert(String source) {
            return Dictionary.valueOf(source.toUpperCase());
        }
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }
}
