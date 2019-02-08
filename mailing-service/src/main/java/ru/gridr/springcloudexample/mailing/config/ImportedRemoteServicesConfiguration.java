package ru.gridr.springcloudexample.mailing.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.jsonrpc4j.ExceptionResolver;
import com.googlecode.jsonrpc4j.spring.JsonProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.gridr.springcloudexample.jsonrpc.exceptions.JsonRpcClientExceptionResolver;
import ru.gridr.springcloudexample.person.PersonService;

@Configuration
public class ImportedRemoteServicesConfiguration {

    private final ObjectMapper mapper;

    @Autowired
    public ImportedRemoteServicesConfiguration(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Bean
    public ExceptionResolver jsonRpcExceptionResolver() {
        return new JsonRpcClientExceptionResolver(mapper);
    }

    @Bean
    public JsonProxyFactoryBean personServiceProxy() {
        JsonProxyFactoryBean serviceProxy = new JsonProxyFactoryBean();
        serviceProxy.setServiceUrl("http://localhost:8877/PersonService.json"); //TODO netflix eureka
        serviceProxy.setServiceInterface(PersonService.class);
        serviceProxy.setExceptionResolver(jsonRpcExceptionResolver()); //TODO common abstract JsonProxyFactoryBean
        return serviceProxy;
    }
}
