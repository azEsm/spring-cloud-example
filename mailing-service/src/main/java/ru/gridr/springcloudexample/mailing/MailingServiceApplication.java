package ru.gridr.springcloudexample.mailing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import ru.gridr.springcloudexample.mailing.config.ImportedRemoteServicesConfiguration;

@SpringBootApplication
@EnableDiscoveryClient
@Import(ImportedRemoteServicesConfiguration.class)
public class MailingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailingServiceApplication.class, args);
    }
}
