package com.john.frederick.controller;

import com.john.frederick.entity.ExampleEntity;
import com.john.frederick.repository.ExampleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * This class holds all testing methods for the example application. To test the CRUD operations, I'm using MockMvc.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestExampleSpringApplication {

    private static final String EXAMPLE_PATH = "/example";

    /**
     * First, autowire the MockMvc
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Second, autowire the repo in
     */
    @Autowired
    ExampleRepository exampleRepo;

    /**
     * Before each test, we should clear the database to ensure that the data we're testing with is exactly what we want
     * @throws Exception
     */
    @Before
    public void deleteAllBeforeTest() throws Exception {
        exampleRepo.deleteAll();
    }

    /**
     * First test, we want to make sure we can actually create an entity in the database
     * @throws Exception
     */
    @Test
    public void shouldCreateEntity() throws Exception {

        mockMvc.perform(post(EXAMPLE_PATH).content(getExampleEntity())).andExpect(
                status().isCreated()).andExpect(
                header().string("Location", containsString("example/")));
    }

    /**
     * Next, we want to test getting one from the database. To do so, first we have to insert one, and then expect the
     * jsonPath to have the correct values within.
     * @throws Exception
     */
    @Test
    public void shouldRetrieveEntity() throws Exception{
        MvcResult mvcResult = mockMvc.perform(post(EXAMPLE_PATH).content(getExampleEntity())).andExpect(
                status().isCreated()).andReturn();

        String location = mvcResult.getResponse().getHeader("Location");
        mockMvc.perform(get(location)).andExpect(status().isOk()).andExpect(
                jsonPath("$.firstName").value("John")).andExpect(
                jsonPath("$.lastName").value("Frederick")).andExpect(
                jsonPath("$.age").value("25"));

    }

    /**
     * Next, we check to see if updating the entity works. Using a helper method with first name and age different,
     * we insert the original helper method entity, patch it with the updated helper method, and then get it from the
     * database and check the values.
     * @throws Exception
     */
    @Test
    public void shouldUpdateEntity() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(EXAMPLE_PATH).content(getExampleEntity())).andExpect(
                status().isCreated()).andReturn();

        String location = mvcResult.getResponse().getHeader("Location");

        mockMvc.perform(patch(location).content(getUpdatedExampleEntity())).andExpect(
                status().isNoContent());

        mockMvc.perform(get(location)).andExpect(status().isOk()).andExpect(
                jsonPath("$.firstName").value("Jack")).andExpect(
                jsonPath("$.lastName").value("Frederick")).andExpect(
                jsonPath("$.age").value("26"));
    }

    /**
     * Next, we check to make sure deletion from the database is allowed. First, we insert an entity into the database,
     * then remove it, then check to make sure there's nothing within the database.
     * @throws Exception
     */
    @Test
    public void shouldDeleteEntity() throws Exception {

        MvcResult mvcResult = mockMvc.perform(post(EXAMPLE_PATH).content(getExampleEntity())).andExpect(
                status().isCreated()).andReturn();

        String location = mvcResult.getResponse().getHeader("Location");
        mockMvc.perform(delete(location)).andExpect(status().isNoContent());

        mockMvc.perform(get(location)).andExpect(status().isNotFound());
    }

    /**
     * We need to test our custom method. To do so, we insert 3 different entities, then call the custom endpoint
     * with the last name used, and check to make sure that only our assumed number of entities are within (2, in our case)
     * @throws Exception
     */
    @Test
    public void shouldRetrieveByLastName() throws Exception{
        mockMvc.perform(post(EXAMPLE_PATH).content(getExampleEntity())).andExpect(
                status().isCreated()).andReturn();
        mockMvc.perform(post(EXAMPLE_PATH).content(getUpdatedExampleEntity())).andExpect(
                status().isCreated()).andReturn();
        mockMvc.perform(post(EXAMPLE_PATH).content(getIncorrectExampleEntity())).andExpect(
                status().isCreated()).andReturn();

        List<ExampleEntity> entityList = exampleRepo.findByLastName("Frederick");
        assertTrue(entityList.size() == 2);
    }

    /**
     * Helper class for inserting entity
     */
    private String getExampleEntity(){
        return "{ \"firstName\": \"John\", \"lastName\": \"Frederick\", \"age\":25}";
    }

    /**
     * Helper class for updating entity
     */
    private String getUpdatedExampleEntity(){
        return "{ \"firstName\": \"Jack\", \"lastName\": \"Frederick\", \"age\":26}";
    }

    /**
     * Helper class for custom method verification
     */
    private String getIncorrectExampleEntity(){
        return "{ \"firstName\": \"John\", \"lastName\": \"Smith\", \"age\":25}";

    }
}
