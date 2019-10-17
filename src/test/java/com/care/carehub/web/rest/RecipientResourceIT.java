package com.care.carehub.web.rest;

import com.care.carehub.CarehubApp;
import com.care.carehub.domain.Recipient;
import com.care.carehub.repository.RecipientRepository;
import com.care.carehub.service.RecipientService;
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
 * Integration tests for the {@link RecipientResource} REST controller.
 */
@SpringBootTest(classes = CarehubApp.class)
public class RecipientResourceIT {

    private static final String DEFAULT_RELATIONSHIP_TO_YOU = "AAAAAAAAAA";
    private static final String UPDATED_RELATIONSHIP_TO_YOU = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.DRAFT;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

    private static final Instant DEFAULT_STATUS_TLM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STATUS_TLM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_WHEN_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_WHEN_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private RecipientRepository recipientRepository;

    @Autowired
    private RecipientService recipientService;

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

    private MockMvc restRecipientMockMvc;

    private Recipient recipient;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RecipientResource recipientResource = new RecipientResource(recipientService);
        this.restRecipientMockMvc = MockMvcBuilders.standaloneSetup(recipientResource)
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
    public static Recipient createEntity(EntityManager em) {
        Recipient recipient = new Recipient()
            .relationshipToYou(DEFAULT_RELATIONSHIP_TO_YOU)
            .status(DEFAULT_STATUS)
            .statusTLM(DEFAULT_STATUS_TLM)
            .whenCreated(DEFAULT_WHEN_CREATED);
        return recipient;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recipient createUpdatedEntity(EntityManager em) {
        Recipient recipient = new Recipient()
            .relationshipToYou(UPDATED_RELATIONSHIP_TO_YOU)
            .status(UPDATED_STATUS)
            .statusTLM(UPDATED_STATUS_TLM)
            .whenCreated(UPDATED_WHEN_CREATED);
        return recipient;
    }

    @BeforeEach
    public void initTest() {
        recipient = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecipient() throws Exception {
        int databaseSizeBeforeCreate = recipientRepository.findAll().size();

        // Create the Recipient
        restRecipientMockMvc.perform(post("/api/recipients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipient)))
            .andExpect(status().isCreated());

        // Validate the Recipient in the database
        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeCreate + 1);
        Recipient testRecipient = recipientList.get(recipientList.size() - 1);
        assertThat(testRecipient.getRelationshipToYou()).isEqualTo(DEFAULT_RELATIONSHIP_TO_YOU);
        assertThat(testRecipient.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testRecipient.getStatusTLM()).isEqualTo(DEFAULT_STATUS_TLM);
        assertThat(testRecipient.getWhenCreated()).isEqualTo(DEFAULT_WHEN_CREATED);
    }

    @Test
    @Transactional
    public void createRecipientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recipientRepository.findAll().size();

        // Create the Recipient with an existing ID
        recipient.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecipientMockMvc.perform(post("/api/recipients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipient)))
            .andExpect(status().isBadRequest());

        // Validate the Recipient in the database
        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = recipientRepository.findAll().size();
        // set the field null
        recipient.setStatus(null);

        // Create the Recipient, which fails.

        restRecipientMockMvc.perform(post("/api/recipients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipient)))
            .andExpect(status().isBadRequest());

        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRecipients() throws Exception {
        // Initialize the database
        recipientRepository.saveAndFlush(recipient);

        // Get all the recipientList
        restRecipientMockMvc.perform(get("/api/recipients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipient.getId().intValue())))
            .andExpect(jsonPath("$.[*].relationshipToYou").value(hasItem(DEFAULT_RELATIONSHIP_TO_YOU)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].statusTLM").value(hasItem(DEFAULT_STATUS_TLM.toString())))
            .andExpect(jsonPath("$.[*].whenCreated").value(hasItem(DEFAULT_WHEN_CREATED.toString())));
    }
    
    @Test
    @Transactional
    public void getRecipient() throws Exception {
        // Initialize the database
        recipientRepository.saveAndFlush(recipient);

        // Get the recipient
        restRecipientMockMvc.perform(get("/api/recipients/{id}", recipient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recipient.getId().intValue()))
            .andExpect(jsonPath("$.relationshipToYou").value(DEFAULT_RELATIONSHIP_TO_YOU))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.statusTLM").value(DEFAULT_STATUS_TLM.toString()))
            .andExpect(jsonPath("$.whenCreated").value(DEFAULT_WHEN_CREATED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRecipient() throws Exception {
        // Get the recipient
        restRecipientMockMvc.perform(get("/api/recipients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecipient() throws Exception {
        // Initialize the database
        recipientService.save(recipient);

        int databaseSizeBeforeUpdate = recipientRepository.findAll().size();

        // Update the recipient
        Recipient updatedRecipient = recipientRepository.findById(recipient.getId()).get();
        // Disconnect from session so that the updates on updatedRecipient are not directly saved in db
        em.detach(updatedRecipient);
        updatedRecipient
            .relationshipToYou(UPDATED_RELATIONSHIP_TO_YOU)
            .status(UPDATED_STATUS)
            .statusTLM(UPDATED_STATUS_TLM)
            .whenCreated(UPDATED_WHEN_CREATED);

        restRecipientMockMvc.perform(put("/api/recipients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRecipient)))
            .andExpect(status().isOk());

        // Validate the Recipient in the database
        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeUpdate);
        Recipient testRecipient = recipientList.get(recipientList.size() - 1);
        assertThat(testRecipient.getRelationshipToYou()).isEqualTo(UPDATED_RELATIONSHIP_TO_YOU);
        assertThat(testRecipient.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRecipient.getStatusTLM()).isEqualTo(UPDATED_STATUS_TLM);
        assertThat(testRecipient.getWhenCreated()).isEqualTo(UPDATED_WHEN_CREATED);
    }

    @Test
    @Transactional
    public void updateNonExistingRecipient() throws Exception {
        int databaseSizeBeforeUpdate = recipientRepository.findAll().size();

        // Create the Recipient

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecipientMockMvc.perform(put("/api/recipients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipient)))
            .andExpect(status().isBadRequest());

        // Validate the Recipient in the database
        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecipient() throws Exception {
        // Initialize the database
        recipientService.save(recipient);

        int databaseSizeBeforeDelete = recipientRepository.findAll().size();

        // Delete the recipient
        restRecipientMockMvc.perform(delete("/api/recipients/{id}", recipient.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Recipient.class);
        Recipient recipient1 = new Recipient();
        recipient1.setId(1L);
        Recipient recipient2 = new Recipient();
        recipient2.setId(recipient1.getId());
        assertThat(recipient1).isEqualTo(recipient2);
        recipient2.setId(2L);
        assertThat(recipient1).isNotEqualTo(recipient2);
        recipient1.setId(null);
        assertThat(recipient1).isNotEqualTo(recipient2);
    }
}
