package com.care.carehub.web.rest;

import com.care.carehub.CarehubApp;
import com.care.carehub.domain.Responsibilities;
import com.care.carehub.repository.ResponsibilitiesRepository;
import com.care.carehub.service.ResponsibilitiesService;
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
 * Integration tests for the {@link ResponsibilitiesResource} REST controller.
 */
@SpringBootTest(classes = CarehubApp.class)
public class ResponsibilitiesResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.DRAFT;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

    private static final Instant DEFAULT_WHEN_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_WHEN_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ResponsibilitiesRepository responsibilitiesRepository;

    @Autowired
    private ResponsibilitiesService responsibilitiesService;

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

    private MockMvc restResponsibilitiesMockMvc;

    private Responsibilities responsibilities;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResponsibilitiesResource responsibilitiesResource = new ResponsibilitiesResource(responsibilitiesService);
        this.restResponsibilitiesMockMvc = MockMvcBuilders.standaloneSetup(responsibilitiesResource)
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
    public static Responsibilities createEntity(EntityManager em) {
        Responsibilities responsibilities = new Responsibilities()
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS)
            .whenCreated(DEFAULT_WHEN_CREATED);
        return responsibilities;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Responsibilities createUpdatedEntity(EntityManager em) {
        Responsibilities responsibilities = new Responsibilities()
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .whenCreated(UPDATED_WHEN_CREATED);
        return responsibilities;
    }

    @BeforeEach
    public void initTest() {
        responsibilities = createEntity(em);
    }

    @Test
    @Transactional
    public void createResponsibilities() throws Exception {
        int databaseSizeBeforeCreate = responsibilitiesRepository.findAll().size();

        // Create the Responsibilities
        restResponsibilitiesMockMvc.perform(post("/api/responsibilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsibilities)))
            .andExpect(status().isCreated());

        // Validate the Responsibilities in the database
        List<Responsibilities> responsibilitiesList = responsibilitiesRepository.findAll();
        assertThat(responsibilitiesList).hasSize(databaseSizeBeforeCreate + 1);
        Responsibilities testResponsibilities = responsibilitiesList.get(responsibilitiesList.size() - 1);
        assertThat(testResponsibilities.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testResponsibilities.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testResponsibilities.getWhenCreated()).isEqualTo(DEFAULT_WHEN_CREATED);
    }

    @Test
    @Transactional
    public void createResponsibilitiesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = responsibilitiesRepository.findAll().size();

        // Create the Responsibilities with an existing ID
        responsibilities.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResponsibilitiesMockMvc.perform(post("/api/responsibilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsibilities)))
            .andExpect(status().isBadRequest());

        // Validate the Responsibilities in the database
        List<Responsibilities> responsibilitiesList = responsibilitiesRepository.findAll();
        assertThat(responsibilitiesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsibilitiesRepository.findAll().size();
        // set the field null
        responsibilities.setDescription(null);

        // Create the Responsibilities, which fails.

        restResponsibilitiesMockMvc.perform(post("/api/responsibilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsibilities)))
            .andExpect(status().isBadRequest());

        List<Responsibilities> responsibilitiesList = responsibilitiesRepository.findAll();
        assertThat(responsibilitiesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsibilitiesRepository.findAll().size();
        // set the field null
        responsibilities.setStatus(null);

        // Create the Responsibilities, which fails.

        restResponsibilitiesMockMvc.perform(post("/api/responsibilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsibilities)))
            .andExpect(status().isBadRequest());

        List<Responsibilities> responsibilitiesList = responsibilitiesRepository.findAll();
        assertThat(responsibilitiesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResponsibilities() throws Exception {
        // Initialize the database
        responsibilitiesRepository.saveAndFlush(responsibilities);

        // Get all the responsibilitiesList
        restResponsibilitiesMockMvc.perform(get("/api/responsibilities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responsibilities.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].whenCreated").value(hasItem(DEFAULT_WHEN_CREATED.toString())));
    }
    
    @Test
    @Transactional
    public void getResponsibilities() throws Exception {
        // Initialize the database
        responsibilitiesRepository.saveAndFlush(responsibilities);

        // Get the responsibilities
        restResponsibilitiesMockMvc.perform(get("/api/responsibilities/{id}", responsibilities.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(responsibilities.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.whenCreated").value(DEFAULT_WHEN_CREATED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingResponsibilities() throws Exception {
        // Get the responsibilities
        restResponsibilitiesMockMvc.perform(get("/api/responsibilities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResponsibilities() throws Exception {
        // Initialize the database
        responsibilitiesService.save(responsibilities);

        int databaseSizeBeforeUpdate = responsibilitiesRepository.findAll().size();

        // Update the responsibilities
        Responsibilities updatedResponsibilities = responsibilitiesRepository.findById(responsibilities.getId()).get();
        // Disconnect from session so that the updates on updatedResponsibilities are not directly saved in db
        em.detach(updatedResponsibilities);
        updatedResponsibilities
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .whenCreated(UPDATED_WHEN_CREATED);

        restResponsibilitiesMockMvc.perform(put("/api/responsibilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResponsibilities)))
            .andExpect(status().isOk());

        // Validate the Responsibilities in the database
        List<Responsibilities> responsibilitiesList = responsibilitiesRepository.findAll();
        assertThat(responsibilitiesList).hasSize(databaseSizeBeforeUpdate);
        Responsibilities testResponsibilities = responsibilitiesList.get(responsibilitiesList.size() - 1);
        assertThat(testResponsibilities.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testResponsibilities.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testResponsibilities.getWhenCreated()).isEqualTo(UPDATED_WHEN_CREATED);
    }

    @Test
    @Transactional
    public void updateNonExistingResponsibilities() throws Exception {
        int databaseSizeBeforeUpdate = responsibilitiesRepository.findAll().size();

        // Create the Responsibilities

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponsibilitiesMockMvc.perform(put("/api/responsibilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsibilities)))
            .andExpect(status().isBadRequest());

        // Validate the Responsibilities in the database
        List<Responsibilities> responsibilitiesList = responsibilitiesRepository.findAll();
        assertThat(responsibilitiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResponsibilities() throws Exception {
        // Initialize the database
        responsibilitiesService.save(responsibilities);

        int databaseSizeBeforeDelete = responsibilitiesRepository.findAll().size();

        // Delete the responsibilities
        restResponsibilitiesMockMvc.perform(delete("/api/responsibilities/{id}", responsibilities.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Responsibilities> responsibilitiesList = responsibilitiesRepository.findAll();
        assertThat(responsibilitiesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Responsibilities.class);
        Responsibilities responsibilities1 = new Responsibilities();
        responsibilities1.setId(1L);
        Responsibilities responsibilities2 = new Responsibilities();
        responsibilities2.setId(responsibilities1.getId());
        assertThat(responsibilities1).isEqualTo(responsibilities2);
        responsibilities2.setId(2L);
        assertThat(responsibilities1).isNotEqualTo(responsibilities2);
        responsibilities1.setId(null);
        assertThat(responsibilities1).isNotEqualTo(responsibilities2);
    }
}
