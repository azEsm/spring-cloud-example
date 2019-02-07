package ru.gridr.springcloudexample.personinfo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.gridr.springcloudexample.personinfo.service.Person;
import ru.gridr.springcloudexample.personinfo.service.PersonRepository;

import java.util.List;

/**
 * Database data initialisation service
 */
@Repository
public class DataBaseInitialization implements CommandLineRunner {

    private final PersonRepository repository;

    public DataBaseInitialization(PersonRepository repository) {
        this.repository = repository;
    }

    /**
     * Filling database with initial data
     *
     * @param args not used
     */
    @Override
    public void run(String... args) {
        List<Person> allPersons = repository.findAll();
        if (!CollectionUtils.isEmpty(allPersons)) {
            return;
        }

        repository.save(new Person("Ivan Petrov", "ivan@mail.com"));
        repository.save(new Person("Petr Ivanov", "petr@google.com"));
        repository.save(new Person("John Smith", "john@smith.c"));
    }
}
