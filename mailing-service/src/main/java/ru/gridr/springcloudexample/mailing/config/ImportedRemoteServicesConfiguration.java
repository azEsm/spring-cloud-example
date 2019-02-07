package ru.gridr.springcloudexample.mailing.config;

import com.googlecode.jsonrpc4j.spring.JsonProxyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.gridr.springcloudexample.person.PersonService;

@Configuration
public class ImportedRemoteServicesConfiguration {
    @Bean
    public JsonProxyFactoryBean personServiceProxy() {
        JsonProxyFactoryBean serviceProxy = new JsonProxyFactoryBean();
        serviceProxy.setServiceUrl("http://localhost:8877/PersonService.json"); //TODO netflix eureka
        serviceProxy.setServiceInterface(PersonService.class);
        return serviceProxy;
    }
}
