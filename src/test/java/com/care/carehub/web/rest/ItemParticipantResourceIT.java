package com.care.carehub.web.rest;

import com.care.carehub.CarehubApp;
import com.care.carehub.domain.ItemParticipant;
import com.care.carehub.repository.ItemParticipantRepository;
import com.care.carehub.service.ItemParticipantService;
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

import com.care.carehub.domain.enumeration.Permission;
/**
 * Integration tests for the {@link ItemParticipantResource} REST controller.
 */
@SpringBootTest(classes = CarehubApp.class)
public class ItemParticipantResourceIT {

    private static final Permission DEFAULT_PERMISSION = Permission.NONE;
    private static final Permission UPDATED_PERMISSION = Permission.EDIT;

    private static final Instant DEFAULT_WHEN_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_WHEN_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ItemParticipantRepository itemParticipantRepository;

    @Autowired
    private ItemParticipantService itemParticipantService;

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

    private MockMvc restItemParticipantMockMvc;

    private ItemParticipant itemParticipant;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemParticipantResource itemParticipantResource = new ItemParticipantResource(itemParticipantService);
        this.restItemParticipantMockMvc = MockMvcBuilders.standaloneSetup(itemParticipantResource)
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
    public static ItemParticipant createEntity(EntityManager em) {
        ItemParticipant itemParticipant = new ItemParticipant()
            .permission(DEFAULT_PERMISSION)
            .whenCreated(DEFAULT_WHEN_CREATED);
        return itemParticipant;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemParticipant createUpdatedEntity(EntityManager em) {
        ItemParticipant itemParticipant = new ItemParticipant()
            .permission(UPDATED_PERMISSION)
            .whenCreated(UPDATED_WHEN_CREATED);
        return itemParticipant;
    }

    @BeforeEach
    public void initTest() {
        itemParticipant = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemParticipant() throws Exception {
        int databaseSizeBeforeCreate = itemParticipantRepository.findAll().size();

        // Create the ItemParticipant
        restItemParticipantMockMvc.perform(post("/api/item-participants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemParticipant)))
            .andExpect(status().isCreated());

        // Validate the ItemParticipant in the database
        List<ItemParticipant> itemParticipantList = itemParticipantRepository.findAll();
        assertThat(itemParticipantList).hasSize(databaseSizeBeforeCreate + 1);
        ItemParticipant testItemParticipant = itemParticipantList.get(itemParticipantList.size() - 1);
        assertThat(testItemParticipant.getPermission()).isEqualTo(DEFAULT_PERMISSION);
        assertThat(testItemParticipant.getWhenCreated()).isEqualTo(DEFAULT_WHEN_CREATED);
    }

    @Test
    @Transactional
    public void createItemParticipantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemParticipantRepository.findAll().size();

        // Create the ItemParticipant with an existing ID
        itemParticipant.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemParticipantMockMvc.perform(post("/api/item-participants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemParticipant)))
            .andExpect(status().isBadRequest());

        // Validate the ItemParticipant in the database
        List<ItemParticipant> itemParticipantList = itemParticipantRepository.findAll();
        assertThat(itemParticipantList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPermissionIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemParticipantRepository.findAll().size();
        // set the field null
        itemParticipant.setPermission(null);

        // Create the ItemParticipant, which fails.

        restItemParticipantMockMvc.perform(post("/api/item-participants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemParticipant)))
            .andExpect(status().isBadRequest());

        List<ItemParticipant> itemParticipantList = itemParticipantRepository.findAll();
        assertThat(itemParticipantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllItemParticipants() throws Exception {
        // Initialize the database
        itemParticipantRepository.saveAndFlush(itemParticipant);

        // Get all the itemParticipantList
        restItemParticipantMockMvc.perform(get("/api/item-participants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemParticipant.getId().intValue())))
            .andExpect(jsonPath("$.[*].permission").value(hasItem(DEFAULT_PERMISSION.toString())))
            .andExpect(jsonPath("$.[*].whenCreated").value(hasItem(DEFAULT_WHEN_CREATED.toString())));
    }
    
    @Test
    @Transactional
    public void getItemParticipant() throws Exception {
        // Initialize the database
        itemParticipantRepository.saveAndFlush(itemParticipant);

        // Get the itemParticipant
        restItemParticipantMockMvc.perform(get("/api/item-participants/{id}", itemParticipant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(itemParticipant.getId().intValue()))
            .andExpect(jsonPath("$.permission").value(DEFAULT_PERMISSION.toString()))
            .andExpect(jsonPath("$.whenCreated").value(DEFAULT_WHEN_CREATED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingItemParticipant() throws Exception {
        // Get the itemParticipant
        restItemParticipantMockMvc.perform(get("/api/item-participants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemParticipant() throws Exception {
        // Initialize the database
        itemParticipantService.save(itemParticipant);

        int databaseSizeBeforeUpdate = itemParticipantRepository.findAll().size();

        // Update the itemParticipant
        ItemParticipant updatedItemParticipant = itemParticipantRepository.findById(itemParticipant.getId()).get();
        // Disconnect from session so that the updates on updatedItemParticipant are not directly saved in db
        em.detach(updatedItemParticipant);
        updatedItemParticipant
            .permission(UPDATED_PERMISSION)
            .whenCreated(UPDATED_WHEN_CREATED);

        restItemParticipantMockMvc.perform(put("/api/item-participants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedItemParticipant)))
            .andExpect(status().isOk());

        // Validate the ItemParticipant in the database
        List<ItemParticipant> itemParticipantList = itemParticipantRepository.findAll();
        assertThat(itemParticipantList).hasSize(databaseSizeBeforeUpdate);
        ItemParticipant testItemParticipant = itemParticipantList.get(itemParticipantList.size() - 1);
        assertThat(testItemParticipant.getPermission()).isEqualTo(UPDATED_PERMISSION);
        assertThat(testItemParticipant.getWhenCreated()).isEqualTo(UPDATED_WHEN_CREATED);
    }

    @Test
    @Transactional
    public void updateNonExistingItemParticipant() throws Exception {
        int databaseSizeBeforeUpdate = itemParticipantRepository.findAll().size();

        // Create the ItemParticipant

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemParticipantMockMvc.perform(put("/api/item-participants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemParticipant)))
            .andExpect(status().isBadRequest());

        // Validate the ItemParticipant in the database
        List<ItemParticipant> itemParticipantList = itemParticipantRepository.findAll();
        assertThat(itemParticipantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteItemParticipant() throws Exception {
        // Initialize the database
        itemParticipantService.save(itemParticipant);

        int databaseSizeBeforeDelete = itemParticipantRepository.findAll().size();

        // Delete the itemParticipant
        restItemParticipantMockMvc.perform(delete("/api/item-participants/{id}", itemParticipant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemParticipant> itemParticipantList = itemParticipantRepository.findAll();
        assertThat(itemParticipantList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemParticipant.class);
        ItemParticipant itemParticipant1 = new ItemParticipant();
        itemParticipant1.setId(1L);
        ItemParticipant itemParticipant2 = new ItemParticipant();
        itemParticipant2.setId(itemParticipant1.getId());
        assertThat(itemParticipant1).isEqualTo(itemParticipant2);
        itemParticipant2.setId(2L);
        assertThat(itemParticipant1).isNotEqualTo(itemParticipant2);
        itemParticipant1.setId(null);
        assertThat(itemParticipant1).isNotEqualTo(itemParticipant2);
    }
}
