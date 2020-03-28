package com.dnstth.vtmg.web.rest;

import com.dnstth.vtmg.VtmgApp;
import com.dnstth.vtmg.domain.Kind;
import com.dnstth.vtmg.repository.KindRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link KindResource} REST controller.
 */
@SpringBootTest(classes = VtmgApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class KindResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    @Autowired
    private KindRepository kindRepository;

    @Autowired
    private MockMvc restKindMockMvc;

    private Kind kind;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kind createEntity() {
        Kind kind = new Kind()
            .description(DEFAULT_DESCRIPTION)
            .details(DEFAULT_DETAILS);
        return kind;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kind createUpdatedEntity() {
        Kind kind = new Kind()
            .description(UPDATED_DESCRIPTION)
            .details(UPDATED_DETAILS);
        return kind;
    }

    @BeforeEach
    public void initTest() {
        kindRepository.deleteAll();
        kind = createEntity();
    }

    @Test
    public void createKind() throws Exception {
        int databaseSizeBeforeCreate = kindRepository.findAll().size();

        // Create the Kind
        restKindMockMvc.perform(post("/api/kinds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kind)))
            .andExpect(status().isCreated());

        // Validate the Kind in the database
        List<Kind> kindList = kindRepository.findAll();
        assertThat(kindList).hasSize(databaseSizeBeforeCreate + 1);
        Kind testKind = kindList.get(kindList.size() - 1);
        assertThat(testKind.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testKind.getDetails()).isEqualTo(DEFAULT_DETAILS);
    }

    @Test
    public void createKindWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kindRepository.findAll().size();

        // Create the Kind with an existing ID
        kind.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restKindMockMvc.perform(post("/api/kinds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kind)))
            .andExpect(status().isBadRequest());

        // Validate the Kind in the database
        List<Kind> kindList = kindRepository.findAll();
        assertThat(kindList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = kindRepository.findAll().size();
        // set the field null
        kind.setDescription(null);

        // Create the Kind, which fails.

        restKindMockMvc.perform(post("/api/kinds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kind)))
            .andExpect(status().isBadRequest());

        List<Kind> kindList = kindRepository.findAll();
        assertThat(kindList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllKinds() throws Exception {
        // Initialize the database
        kindRepository.save(kind);

        // Get all the kindList
        restKindMockMvc.perform(get("/api/kinds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kind.getId())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)));
    }
    
    @Test
    public void getKind() throws Exception {
        // Initialize the database
        kindRepository.save(kind);

        // Get the kind
        restKindMockMvc.perform(get("/api/kinds/{id}", kind.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kind.getId()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS));
    }

    @Test
    public void getNonExistingKind() throws Exception {
        // Get the kind
        restKindMockMvc.perform(get("/api/kinds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateKind() throws Exception {
        // Initialize the database
        kindRepository.save(kind);

        int databaseSizeBeforeUpdate = kindRepository.findAll().size();

        // Update the kind
        Kind updatedKind = kindRepository.findById(kind.getId()).get();
        updatedKind
            .description(UPDATED_DESCRIPTION)
            .details(UPDATED_DETAILS);

        restKindMockMvc.perform(put("/api/kinds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedKind)))
            .andExpect(status().isOk());

        // Validate the Kind in the database
        List<Kind> kindList = kindRepository.findAll();
        assertThat(kindList).hasSize(databaseSizeBeforeUpdate);
        Kind testKind = kindList.get(kindList.size() - 1);
        assertThat(testKind.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testKind.getDetails()).isEqualTo(UPDATED_DETAILS);
    }

    @Test
    public void updateNonExistingKind() throws Exception {
        int databaseSizeBeforeUpdate = kindRepository.findAll().size();

        // Create the Kind

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKindMockMvc.perform(put("/api/kinds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kind)))
            .andExpect(status().isBadRequest());

        // Validate the Kind in the database
        List<Kind> kindList = kindRepository.findAll();
        assertThat(kindList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteKind() throws Exception {
        // Initialize the database
        kindRepository.save(kind);

        int databaseSizeBeforeDelete = kindRepository.findAll().size();

        // Delete the kind
        restKindMockMvc.perform(delete("/api/kinds/{id}", kind.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Kind> kindList = kindRepository.findAll();
        assertThat(kindList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
