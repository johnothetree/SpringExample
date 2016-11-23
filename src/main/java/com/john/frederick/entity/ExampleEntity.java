package com.john.frederick.entity;

import org.springframework.data.annotation.Id;

/**
 * Example entity that will be used in the MongoDB repo
 */
public class ExampleEntity {

    /**
     * Id value for the database
     */
    @Id
    private String id;

    private String firstName;

    private String lastName;

    private int age;

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
