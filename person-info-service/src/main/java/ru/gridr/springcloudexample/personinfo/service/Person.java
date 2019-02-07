package ru.gridr.springcloudexample.personinfo.service;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * Person data
 */
@Entity
public class Person {
    @Id
    @GeneratedValue
    private Long id;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
