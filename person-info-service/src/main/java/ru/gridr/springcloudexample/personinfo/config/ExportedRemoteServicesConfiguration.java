package ru.gridr.springcloudexample.personinfo.config;

import com.googlecode.jsonrpc4j.spring.JsonServiceExporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;
import ru.gridr.springcloudexample.person.PersonService;
import ru.gridr.springcloudexample.personinfo.service.DatabasePersonService;
import ru.gridr.springcloudexample.personinfo.service.PersonRepository;

@Configuration
public class ExportedRemoteServicesConfiguration {
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
        return serviceExporter;
    }
}
