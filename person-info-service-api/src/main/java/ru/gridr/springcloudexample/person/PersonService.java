package ru.gridr.springcloudexample.person;

import java.util.List;

/**
 * Service that manages user's data
 */
public interface PersonService {

    /**
     * Load data of all persons
     *
     * @return date of all users
     */
    List<PersonDto> allPersons();
}
