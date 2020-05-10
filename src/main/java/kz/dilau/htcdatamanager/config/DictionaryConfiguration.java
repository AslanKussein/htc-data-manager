package kz.dilau.htcdatamanager.config;

import kz.dilau.htcdatamanager.service.dictionary.DictionaryServiceFactory;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("kz.dilau.htcdatamanager.service.impl.dictionary")
public class DictionaryConfiguration {
    @Bean
    public ServiceLocatorFactoryBean serviceLocatorFactoryBean() {
        ServiceLocatorFactoryBean serviceLocatorFactoryBean = new ServiceLocatorFactoryBean();
        serviceLocatorFactoryBean
                .setServiceLocatorInterface(DictionaryServiceFactory.class);
        return serviceLocatorFactoryBean;
    }
}
