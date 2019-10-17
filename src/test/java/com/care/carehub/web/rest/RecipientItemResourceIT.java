package com.care.carehub.web.rest;

import com.care.carehub.CarehubApp;
import com.care.carehub.domain.RecipientItem;
import com.care.carehub.repository.RecipientItemRepository;
import com.care.carehub.service.RecipientItemService;
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

/**
 * Integration tests for the {@link RecipientItemResource} REST controller.
 */
@SpringBootTest(classes = CarehubApp.class)
public class RecipientItemResourceIT {

    private static final Boolean DEFAULT_PERMISSION_TO_ALL = false;
    private static final Boolean UPDATED_PERMISSION_TO_ALL = true;

    private static final Instant DEFAULT_WHEN_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_WHEN_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private RecipientItemRepository recipientItemRepository;

    @Autowired
    private RecipientItemService recipientItemService;

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

    private MockMvc restRecipientItemMockMvc;

    private RecipientItem recipientItem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RecipientItemResource recipientItemResource = new RecipientItemResource(recipientItemService);
        this.restRecipientItemMockMvc = MockMvcBuilders.standaloneSetup(recipientItemResource)
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
    public static RecipientItem createEntity(EntityManager em) {
        RecipientItem recipientItem = new RecipientItem()
            .permissionToAll(DEFAULT_PERMISSION_TO_ALL)
            .whenCreated(DEFAULT_WHEN_CREATED);
        return recipientItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecipientItem createUpdatedEntity(EntityManager em) {
        RecipientItem recipientItem = new RecipientItem()
            .permissionToAll(UPDATED_PERMISSION_TO_ALL)
            .whenCreated(UPDATED_WHEN_CREATED);
        return recipientItem;
    }

    @BeforeEach
    public void initTest() {
        recipientItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecipientItem() throws Exception {
        int databaseSizeBeforeCreate = recipientItemRepository.findAll().size();

        // Create the RecipientItem
        restRecipientItemMockMvc.perform(post("/api/recipient-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipientItem)))
            .andExpect(status().isCreated());

        // Validate the RecipientItem in the database
        List<RecipientItem> recipientItemList = recipientItemRepository.findAll();
        assertThat(recipientItemList).hasSize(databaseSizeBeforeCreate + 1);
        RecipientItem testRecipientItem = recipientItemList.get(recipientItemList.size() - 1);
        assertThat(testRecipientItem.isPermissionToAll()).isEqualTo(DEFAULT_PERMISSION_TO_ALL);
        assertThat(testRecipientItem.getWhenCreated()).isEqualTo(DEFAULT_WHEN_CREATED);
    }

    @Test
    @Transactional
    public void createRecipientItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recipientItemRepository.findAll().size();

        // Create the RecipientItem with an existing ID
        recipientItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecipientItemMockMvc.perform(post("/api/recipient-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipientItem)))
            .andExpect(status().isBadRequest());

        // Validate the RecipientItem in the database
        List<RecipientItem> recipientItemList = recipientItemRepository.findAll();
        assertThat(recipientItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPermissionToAllIsRequired() throws Exception {
        int databaseSizeBeforeTest = recipientItemRepository.findAll().size();
        // set the field null
        recipientItem.setPermissionToAll(null);

        // Create the RecipientItem, which fails.

        restRecipientItemMockMvc.perform(post("/api/recipient-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipientItem)))
            .andExpect(status().isBadRequest());

        List<RecipientItem> recipientItemList = recipientItemRepository.findAll();
        assertThat(recipientItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRecipientItems() throws Exception {
        // Initialize the database
        recipientItemRepository.saveAndFlush(recipientItem);

        // Get all the recipientItemList
        restRecipientItemMockMvc.perform(get("/api/recipient-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipientItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].permissionToAll").value(hasItem(DEFAULT_PERMISSION_TO_ALL.booleanValue())))
            .andExpect(jsonPath("$.[*].whenCreated").value(hasItem(DEFAULT_WHEN_CREATED.toString())));
    }
    
    @Test
    @Transactional
    public void getRecipientItem() throws Exception {
        // Initialize the database
        recipientItemRepository.saveAndFlush(recipientItem);

        // Get the recipientItem
        restRecipientItemMockMvc.perform(get("/api/recipient-items/{id}", recipientItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recipientItem.getId().intValue()))
            .andExpect(jsonPath("$.permissionToAll").value(DEFAULT_PERMISSION_TO_ALL.booleanValue()))
            .andExpect(jsonPath("$.whenCreated").value(DEFAULT_WHEN_CREATED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRecipientItem() throws Exception {
        // Get the recipientItem
        restRecipientItemMockMvc.perform(get("/api/recipient-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecipientItem() throws Exception {
        // Initialize the database
        recipientItemService.save(recipientItem);

        int databaseSizeBeforeUpdate = recipientItemRepository.findAll().size();

        // Update the recipientItem
        RecipientItem updatedRecipientItem = recipientItemRepository.findById(recipientItem.getId()).get();
        // Disconnect from session so that the updates on updatedRecipientItem are not directly saved in db
        em.detach(updatedRecipientItem);
        updatedRecipientItem
            .permissionToAll(UPDATED_PERMISSION_TO_ALL)
            .whenCreated(UPDATED_WHEN_CREATED);

        restRecipientItemMockMvc.perform(put("/api/recipient-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRecipientItem)))
            .andExpect(status().isOk());

        // Validate the RecipientItem in the database
        List<RecipientItem> recipientItemList = recipientItemRepository.findAll();
        assertThat(recipientItemList).hasSize(databaseSizeBeforeUpdate);
        RecipientItem testRecipientItem = recipientItemList.get(recipientItemList.size() - 1);
        assertThat(testRecipientItem.isPermissionToAll()).isEqualTo(UPDATED_PERMISSION_TO_ALL);
        assertThat(testRecipientItem.getWhenCreated()).isEqualTo(UPDATED_WHEN_CREATED);
    }

    @Test
    @Transactional
    public void updateNonExistingRecipientItem() throws Exception {
        int databaseSizeBeforeUpdate = recipientItemRepository.findAll().size();

        // Create the RecipientItem

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecipientItemMockMvc.perform(put("/api/recipient-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipientItem)))
            .andExpect(status().isBadRequest());

        // Validate the RecipientItem in the database
        List<RecipientItem> recipientItemList = recipientItemRepository.findAll();
        assertThat(recipientItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecipientItem() throws Exception {
        // Initialize the database
        recipientItemService.save(recipientItem);

        int databaseSizeBeforeDelete = recipientItemRepository.findAll().size();

        // Delete the recipientItem
        restRecipientItemMockMvc.perform(delete("/api/recipient-items/{id}", recipientItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RecipientItem> recipientItemList = recipientItemRepository.findAll();
        assertThat(recipientItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecipientItem.class);
        RecipientItem recipientItem1 = new RecipientItem();
        recipientItem1.setId(1L);
        RecipientItem recipientItem2 = new RecipientItem();
        recipientItem2.setId(recipientItem1.getId());
        assertThat(recipientItem1).isEqualTo(recipientItem2);
        recipientItem2.setId(2L);
        assertThat(recipientItem1).isNotEqualTo(recipientItem2);
        recipientItem1.setId(null);
        assertThat(recipientItem1).isNotEqualTo(recipientItem2);
    }
}
