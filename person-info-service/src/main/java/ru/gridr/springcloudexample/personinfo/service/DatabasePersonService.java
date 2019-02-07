package ru.gridr.springcloudexample.personinfo.service;

import ru.gridr.springcloudexample.person.PersonDto;
import ru.gridr.springcloudexample.person.PersonService;

import java.util.ArrayList;
import java.util.List;

/**
 * Service that manages person's data stored in database
 */
public class DatabasePersonService implements PersonService {
    private final PersonRepository repository;

    public DatabasePersonService(PersonRepository repository) {
        this.repository = repository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PersonDto> allPersons() {
        List<PersonDto> result = new ArrayList<>();

        List<Person> allPersons = repository.findAll();
        for (Person person : allPersons) {
            result.add(new PersonDto(person.getName(), person.getEmail()));
        }

        return result;
    }
}
