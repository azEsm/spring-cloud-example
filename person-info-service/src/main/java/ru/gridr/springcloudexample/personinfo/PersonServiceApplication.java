package ru.gridr.springcloudexample.personinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import ru.gridr.springcloudexample.personinfo.config.ExportedRemoteServicesConfiguration;

@SpringBootApplication
@EnableDiscoveryClient
@Import(ExportedRemoteServicesConfiguration.class)
public class PersonServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonServiceApplication.class, args);
    }
}
