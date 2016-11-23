package com.john.frederick.repository;

import com.john.frederick.entity.ExampleEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * For a MongoDB repo, it's best to use an interface extending MongoRepository in order to not need handmade CRUD
 * methods. We also use the annotation below to set the database table name, and the path for the endpoints for this repo
 */
@RepositoryRestResource(collectionResourceRel = "example", path = "example")
public interface ExampleRepository extends MongoRepository<ExampleEntity, String> {

    /**
     * We can also use custom database methods to do as we want. This will take in a parameter "lastName" and return a
     * list of ExampleEntity objects that have the last name that is string equivalent to the input parameter.
     */
    List<ExampleEntity> findByLastName(@Param("lastName") String lastName);
}
