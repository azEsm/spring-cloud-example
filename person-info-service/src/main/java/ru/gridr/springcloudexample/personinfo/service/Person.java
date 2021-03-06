package ru.gridr.springcloudexample.personinfo.service;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Person data
 */
@Entity
public class Person {
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * Full name
     */
    @NotNull
    @Column(length = 100, nullable = false)
    private String name;

    /**
     * Email
     */
    @Email
    private String email;

    /**
     * no-arg constructor for hibernate
     */
    public Person() {

    }

    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
