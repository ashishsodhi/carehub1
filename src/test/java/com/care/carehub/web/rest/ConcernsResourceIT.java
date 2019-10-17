package com.care.carehub.web.rest;

import com.care.carehub.CarehubApp;
import com.care.carehub.domain.Concerns;
import com.care.carehub.repository.ConcernsRepository;
import com.care.carehub.service.ConcernsService;
import com.care.carehub.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.care.carehub.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.care.carehub.domain.enumeration.Status;
/**
 * Integration tests for the {@link ConcernsResource} REST controller.
 */
@SpringBootTest(classes = CarehubApp.class)
public class ConcernsResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.DRAFT;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

    private static final Instant DEFAULT_WHEN_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_WHEN_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ConcernsRepository concernsRepository;

    @Autowired
    private ConcernsService concernsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restConcernsMockMvc;

    private Concerns concerns;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConcernsResource concernsResource = new ConcernsResource(concernsService);
        this.restConcernsMockMvc = MockMvcBuilders.standaloneSetup(concernsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Concerns createEntity(EntityManager em) {
        Concerns concerns = new Concerns()
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS)
            .whenCreated(DEFAULT_WHEN_CREATED);
        return concerns;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Concerns createUpdatedEntity(EntityManager em) {
        Concerns concerns = new Concerns()
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .whenCreated(UPDATED_WHEN_CREATED);
        return concerns;
    }

    @BeforeEach
    public void initTest() {
        concerns = createEntity(em);
    }

    @Test
    @Transactional
    public void createConcerns() throws Exception {
        int databaseSizeBeforeCreate = concernsRepository.findAll().size();

        // Create the Concerns
        restConcernsMockMvc.perform(post("/api/concerns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(concerns)))
            .andExpect(status().isCreated());

        // Validate the Concerns in the database
        List<Concerns> concernsList = concernsRepository.findAll();
        assertThat(concernsList).hasSize(databaseSizeBeforeCreate + 1);
        Concerns testConcerns = concernsList.get(concernsList.size() - 1);
        assertThat(testConcerns.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testConcerns.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testConcerns.getWhenCreated()).isEqualTo(DEFAULT_WHEN_CREATED);
    }

    @Test
    @Transactional
    public void createConcernsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = concernsRepository.findAll().size();

        // Create the Concerns with an existing ID
        concerns.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConcernsMockMvc.perform(post("/api/concerns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(concerns)))
            .andExpect(status().isBadRequest());

        // Validate the Concerns in the database
        List<Concerns> concernsList = concernsRepository.findAll();
        assertThat(concernsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = concernsRepository.findAll().size();
        // set the field null
        concerns.setDescription(null);

        // Create the Concerns, which fails.

        restConcernsMockMvc.perform(post("/api/concerns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(concerns)))
            .andExpect(status().isBadRequest());

        List<Concerns> concernsList = concernsRepository.findAll();
        assertThat(concernsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = concernsRepository.findAll().size();
        // set the field null
        concerns.setStatus(null);

        // Create the Concerns, which fails.

        restConcernsMockMvc.perform(post("/api/concerns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(concerns)))
            .andExpect(status().isBadRequest());

        List<Concerns> concernsList = concernsRepository.findAll();
        assertThat(concernsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConcerns() throws Exception {
        // Initialize the database
        concernsRepository.saveAndFlush(concerns);

        // Get all the concernsList
        restConcernsMockMvc.perform(get("/api/concerns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(concerns.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].whenCreated").value(hasItem(DEFAULT_WHEN_CREATED.toString())));
    }
    
    @Test
    @Transactional
    public void getConcerns() throws Exception {
        // Initialize the database
        concernsRepository.saveAndFlush(concerns);

        // Get the concerns
        restConcernsMockMvc.perform(get("/api/concerns/{id}", concerns.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(concerns.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.whenCreated").value(DEFAULT_WHEN_CREATED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConcerns() throws Exception {
        // Get the concerns
        restConcernsMockMvc.perform(get("/api/concerns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConcerns() throws Exception {
        // Initialize the database
        concernsService.save(concerns);

        int databaseSizeBeforeUpdate = concernsRepository.findAll().size();

        // Update the concerns
        Concerns updatedConcerns = concernsRepository.findById(concerns.getId()).get();
        // Disconnect from session so that the updates on updatedConcerns are not directly saved in db
        em.detach(updatedConcerns);
        updatedConcerns
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .whenCreated(UPDATED_WHEN_CREATED);

        restConcernsMockMvc.perform(put("/api/concerns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedConcerns)))
            .andExpect(status().isOk());

        // Validate the Concerns in the database
        List<Concerns> concernsList = concernsRepository.findAll();
        assertThat(concernsList).hasSize(databaseSizeBeforeUpdate);
        Concerns testConcerns = concernsList.get(concernsList.size() - 1);
        assertThat(testConcerns.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testConcerns.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testConcerns.getWhenCreated()).isEqualTo(UPDATED_WHEN_CREATED);
    }

    @Test
    @Transactional
    public void updateNonExistingConcerns() throws Exception {
        int databaseSizeBeforeUpdate = concernsRepository.findAll().size();

        // Create the Concerns

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConcernsMockMvc.perform(put("/api/concerns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(concerns)))
            .andExpect(status().isBadRequest());

        // Validate the Concerns in the database
        List<Concerns> concernsList = concernsRepository.findAll();
        assertThat(concernsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConcerns() throws Exception {
        // Initialize the database
        concernsService.save(concerns);

        int databaseSizeBeforeDelete = concernsRepository.findAll().size();

        // Delete the concerns
        restConcernsMockMvc.perform(delete("/api/concerns/{id}", concerns.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Concerns> concernsList = concernsRepository.findAll();
        assertThat(concernsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Concerns.class);
        Concerns concerns1 = new Concerns();
        concerns1.setId(1L);
        Concerns concerns2 = new Concerns();
        concerns2.setId(concerns1.getId());
        assertThat(concerns1).isEqualTo(concerns2);
        concerns2.setId(2L);
        assertThat(concerns1).isNotEqualTo(concerns2);
        concerns1.setId(null);
        assertThat(concerns1).isNotEqualTo(concerns2);
    }
}
