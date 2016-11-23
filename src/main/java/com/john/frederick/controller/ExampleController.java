package com.john.frederick.controller;

import com.john.frederick.entity.ExampleEntity;
import com.john.frederick.repository.ExampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This controller handles all custom methods for the Example class. base endpoint is "/example"
 */
@RestController
@RequestMapping("/example")
public class ExampleController {

    /**
     * We autowire the repo here for easier database access
     */
    @Autowired
    ExampleRepository exampleRepo;

    /**
     * The following method is a custom method for finding entities by last name and sorting them by age.
     * This can be done by putting another custom method in the repository and naming it correctly (findByLastNameOrderByAge),
     * but doing this allows for demonstration of correctly using a RestController.
     * The method also has an additional RequestMapping value, which makes the endpoint for this method "/example/sortedFindByLastName"
     * @param lastName the string input of last name to search for
     * @return sorted list of ExampleEntity objects
     */
    @RequestMapping("/sortedFindByLastName")
    public List<ExampleEntity> findByLastNameSorted(@Param("lastName") String lastName){
        List<ExampleEntity> listToBeSorted = exampleRepo.findByLastName(lastName);

        //We can use Java8 streams and lambda functions. No need to add type to e1 or e2 as it's implied by the list type
        listToBeSorted.stream().sorted((e1, e2) -> Integer.compare(e1.getAge(), e2.getAge()));

        return listToBeSorted;
    }
}
