package com.john.frederick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * This class is used to start the Spring application, as well as holds the base request mapping to show "Hello World!"
 */
@SpringBootApplication
public class ExampleSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleSpringApplication.class, args);
    }

}
