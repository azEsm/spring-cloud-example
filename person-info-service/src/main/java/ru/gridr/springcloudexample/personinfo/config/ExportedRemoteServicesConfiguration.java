package ru.gridr.springcloudexample.personinfo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.jsonrpc4j.ErrorResolver;
import com.googlecode.jsonrpc4j.spring.JsonServiceExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;
import ru.gridr.springcloudexample.jsonrpc.exceptions.JsonRpcServerErrorResolver;
import ru.gridr.springcloudexample.person.PersonService;
import ru.gridr.springcloudexample.personinfo.service.DatabasePersonService;
import ru.gridr.springcloudexample.personinfo.service.PersonRepository;

@Configuration
public class ExportedRemoteServicesConfiguration {

    private final ObjectMapper mapper;

    @Autowired
    public ExportedRemoteServicesConfiguration(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Bean
    public ErrorResolver jsonRpcServerErrorResolver() {
        return new JsonRpcServerErrorResolver(mapper);
    }

    @Bean
    public BeanNameUrlHandlerMapping beanNameUrlHandlerMapping() {
        return new BeanNameUrlHandlerMapping();
    }

    @Bean
    public PersonService personService(PersonRepository repository) {
        return new DatabasePersonService(repository);
    }

    @Bean(name = "/PersonService.json")
    public JsonServiceExporter personServiceExporter(PersonRepository repository) {
        JsonServiceExporter serviceExporter = new JsonServiceExporter();
        serviceExporter.setService(personService(repository));
        serviceExporter.setServiceInterface(PersonService.class);
        serviceExporter.setErrorResolver(jsonRpcServerErrorResolver()); //TODO common abstract JsonServiceExporter
        return serviceExporter;
    }
}
