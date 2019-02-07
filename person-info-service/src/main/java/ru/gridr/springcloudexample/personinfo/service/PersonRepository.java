package ru.gridr.springcloudexample.personinfo.service;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Persons DAO
 */
public interface PersonRepository extends JpaRepository<Person, Long> {

}
