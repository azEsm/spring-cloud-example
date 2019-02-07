package ru.gridr.springcloudexample.personinfo.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Persons DAO
 */
public interface PersonRepository extends JpaRepository<Person, UUID> {

}
