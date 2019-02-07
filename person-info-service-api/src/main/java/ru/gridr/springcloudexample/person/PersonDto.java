package ru.gridr.springcloudexample.person;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * Person data
 */
public class PersonDto {
    /**
     * Full name
     */
    @NotNull
    private String name;

    /**
     * Email
     */
    @Email
    private String email;

    /**
     * No-arg constructor for jackson
     */
    public PersonDto() {

    }

    public PersonDto(String name, String email) {
        this.name = name;
        this.email = email;
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
