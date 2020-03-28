package com.dnstth.vtmg.web.rest;

import com.dnstth.vtmg.VtmgApp;
import com.dnstth.vtmg.domain.Person;
import com.dnstth.vtmg.repository.PersonRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dnstth.vtmg.domain.enumeration.Gender;
/**
 * Integration tests for the {@link PersonResource} REST controller.
 */
@SpringBootTest(classes = VtmgApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class PersonResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BITH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BITH_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DEATH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DEATH_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MockMvc restPersonMockMvc;

    private Person person;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Person createEntity() {
        Person person = new Person()
            .name(DEFAULT_NAME)
            .bithDate(DEFAULT_BITH_DATE)
            .deathDate(DEFAULT_DEATH_DATE)
            .details(DEFAULT_DETAILS)
            .gender(DEFAULT_GENDER);
        return person;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Person createUpdatedEntity() {
        Person person = new Person()
            .name(UPDATED_NAME)
            .bithDate(UPDATED_BITH_DATE)
            .deathDate(UPDATED_DEATH_DATE)
            .details(UPDATED_DETAILS)
            .gender(UPDATED_GENDER);
        return person;
    }

    @BeforeEach
    public void initTest() {
        personRepository.deleteAll();
        person = createEntity();
    }

    @Test
    public void createPerson() throws Exception {
        int databaseSizeBeforeCreate = personRepository.findAll().size();

        // Create the Person
        restPersonMockMvc.perform(post("/api/people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isCreated());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeCreate + 1);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPerson.getBithDate()).isEqualTo(DEFAULT_BITH_DATE);
        assertThat(testPerson.getDeathDate()).isEqualTo(DEFAULT_DEATH_DATE);
        assertThat(testPerson.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testPerson.getGender()).isEqualTo(DEFAULT_GENDER);
    }

    @Test
    public void createPersonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personRepository.findAll().size();

        // Create the Person with an existing ID
        person.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonMockMvc.perform(post("/api/people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setName(null);

        // Create the Person, which fails.

        restPersonMockMvc.perform(post("/api/people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllPeople() throws Exception {
        // Initialize the database
        personRepository.save(person);

        // Get all the personList
        restPersonMockMvc.perform(get("/api/people?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(person.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].bithDate").value(hasItem(DEFAULT_BITH_DATE.toString())))
            .andExpect(jsonPath("$.[*].deathDate").value(hasItem(DEFAULT_DEATH_DATE.toString())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())));
    }
    
    @Test
    public void getPerson() throws Exception {
        // Initialize the database
        personRepository.save(person);

        // Get the person
        restPersonMockMvc.perform(get("/api/people/{id}", person.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(person.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.bithDate").value(DEFAULT_BITH_DATE.toString()))
            .andExpect(jsonPath("$.deathDate").value(DEFAULT_DEATH_DATE.toString()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()));
    }

    @Test
    public void getNonExistingPerson() throws Exception {
        // Get the person
        restPersonMockMvc.perform(get("/api/people/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePerson() throws Exception {
        // Initialize the database
        personRepository.save(person);

        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Update the person
        Person updatedPerson = personRepository.findById(person.getId()).get();
        updatedPerson
            .name(UPDATED_NAME)
            .bithDate(UPDATED_BITH_DATE)
            .deathDate(UPDATED_DEATH_DATE)
            .details(UPDATED_DETAILS)
            .gender(UPDATED_GENDER);

        restPersonMockMvc.perform(put("/api/people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPerson)))
            .andExpect(status().isOk());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPerson.getBithDate()).isEqualTo(UPDATED_BITH_DATE);
        assertThat(testPerson.getDeathDate()).isEqualTo(UPDATED_DEATH_DATE);
        assertThat(testPerson.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testPerson.getGender()).isEqualTo(UPDATED_GENDER);
    }

    @Test
    public void updateNonExistingPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Create the Person

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonMockMvc.perform(put("/api/people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deletePerson() throws Exception {
        // Initialize the database
        personRepository.save(person);

        int databaseSizeBeforeDelete = personRepository.findAll().size();

        // Delete the person
        restPersonMockMvc.perform(delete("/api/people/{id}", person.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
