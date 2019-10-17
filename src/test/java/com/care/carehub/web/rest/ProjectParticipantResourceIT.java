package com.care.carehub.web.rest;

import com.care.carehub.CarehubApp;
import com.care.carehub.domain.ProjectParticipant;
import com.care.carehub.repository.ProjectParticipantRepository;
import com.care.carehub.service.ProjectParticipantService;
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
import com.care.carehub.domain.enumeration.ParticipantStatus;
/**
 * Integration tests for the {@link ProjectParticipantResource} REST controller.
 */
@SpringBootTest(classes = CarehubApp.class)
public class ProjectParticipantResourceIT {

    private static final Long DEFAULT_MEMBER_ID = 1L;
    private static final Long UPDATED_MEMBER_ID = 2L;

    private static final Long DEFAULT_INVITER_ID = 1L;
    private static final Long UPDATED_INVITER_ID = 2L;

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_RELATIONSHIP_TO_INVITER = "AAAAAAAAAA";
    private static final String UPDATED_RELATIONSHIP_TO_INVITER = "BBBBBBBBBB";

    private static final Permission DEFAULT_PERMISSION = Permission.NONE;
    private static final Permission UPDATED_PERMISSION = Permission.EDIT;

    private static final ParticipantStatus DEFAULT_STATUS = ParticipantStatus.INVITED;
    private static final ParticipantStatus UPDATED_STATUS = ParticipantStatus.PENDING;

    private static final Instant DEFAULT_STATUS_TLM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STATUS_TLM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_WHEN_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_WHEN_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ProjectParticipantRepository projectParticipantRepository;

    @Autowired
    private ProjectParticipantService projectParticipantService;

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

    private MockMvc restProjectParticipantMockMvc;

    private ProjectParticipant projectParticipant;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjectParticipantResource projectParticipantResource = new ProjectParticipantResource(projectParticipantService);
        this.restProjectParticipantMockMvc = MockMvcBuilders.standaloneSetup(projectParticipantResource)
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
    public static ProjectParticipant createEntity(EntityManager em) {
        ProjectParticipant projectParticipant = new ProjectParticipant()
            .memberId(DEFAULT_MEMBER_ID)
            .inviterId(DEFAULT_INVITER_ID)
            .firstName(DEFAULT_FIRST_NAME)
            .emailAddress(DEFAULT_EMAIL_ADDRESS)
            .relationshipToInviter(DEFAULT_RELATIONSHIP_TO_INVITER)
            .permission(DEFAULT_PERMISSION)
            .status(DEFAULT_STATUS)
            .statusTLM(DEFAULT_STATUS_TLM)
            .whenCreated(DEFAULT_WHEN_CREATED);
        return projectParticipant;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectParticipant createUpdatedEntity(EntityManager em) {
        ProjectParticipant projectParticipant = new ProjectParticipant()
            .memberId(UPDATED_MEMBER_ID)
            .inviterId(UPDATED_INVITER_ID)
            .firstName(UPDATED_FIRST_NAME)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .relationshipToInviter(UPDATED_RELATIONSHIP_TO_INVITER)
            .permission(UPDATED_PERMISSION)
            .status(UPDATED_STATUS)
            .statusTLM(UPDATED_STATUS_TLM)
            .whenCreated(UPDATED_WHEN_CREATED);
        return projectParticipant;
    }

    @BeforeEach
    public void initTest() {
        projectParticipant = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectParticipant() throws Exception {
        int databaseSizeBeforeCreate = projectParticipantRepository.findAll().size();

        // Create the ProjectParticipant
        restProjectParticipantMockMvc.perform(post("/api/project-participants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectParticipant)))
            .andExpect(status().isCreated());

        // Validate the ProjectParticipant in the database
        List<ProjectParticipant> projectParticipantList = projectParticipantRepository.findAll();
        assertThat(projectParticipantList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectParticipant testProjectParticipant = projectParticipantList.get(projectParticipantList.size() - 1);
        assertThat(testProjectParticipant.getMemberId()).isEqualTo(DEFAULT_MEMBER_ID);
        assertThat(testProjectParticipant.getInviterId()).isEqualTo(DEFAULT_INVITER_ID);
        assertThat(testProjectParticipant.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testProjectParticipant.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testProjectParticipant.getRelationshipToInviter()).isEqualTo(DEFAULT_RELATIONSHIP_TO_INVITER);
        assertThat(testProjectParticipant.getPermission()).isEqualTo(DEFAULT_PERMISSION);
        assertThat(testProjectParticipant.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProjectParticipant.getStatusTLM()).isEqualTo(DEFAULT_STATUS_TLM);
        assertThat(testProjectParticipant.getWhenCreated()).isEqualTo(DEFAULT_WHEN_CREATED);
    }

    @Test
    @Transactional
    public void createProjectParticipantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectParticipantRepository.findAll().size();

        // Create the ProjectParticipant with an existing ID
        projectParticipant.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectParticipantMockMvc.perform(post("/api/project-participants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectParticipant)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectParticipant in the database
        List<ProjectParticipant> projectParticipantList = projectParticipantRepository.findAll();
        assertThat(projectParticipantList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkInviterIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectParticipantRepository.findAll().size();
        // set the field null
        projectParticipant.setInviterId(null);

        // Create the ProjectParticipant, which fails.

        restProjectParticipantMockMvc.perform(post("/api/project-participants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectParticipant)))
            .andExpect(status().isBadRequest());

        List<ProjectParticipant> projectParticipantList = projectParticipantRepository.findAll();
        assertThat(projectParticipantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectParticipantRepository.findAll().size();
        // set the field null
        projectParticipant.setFirstName(null);

        // Create the ProjectParticipant, which fails.

        restProjectParticipantMockMvc.perform(post("/api/project-participants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectParticipant)))
            .andExpect(status().isBadRequest());

        List<ProjectParticipant> projectParticipantList = projectParticipantRepository.findAll();
        assertThat(projectParticipantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectParticipantRepository.findAll().size();
        // set the field null
        projectParticipant.setEmailAddress(null);

        // Create the ProjectParticipant, which fails.

        restProjectParticipantMockMvc.perform(post("/api/project-participants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectParticipant)))
            .andExpect(status().isBadRequest());

        List<ProjectParticipant> projectParticipantList = projectParticipantRepository.findAll();
        assertThat(projectParticipantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPermissionIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectParticipantRepository.findAll().size();
        // set the field null
        projectParticipant.setPermission(null);

        // Create the ProjectParticipant, which fails.

        restProjectParticipantMockMvc.perform(post("/api/project-participants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectParticipant)))
            .andExpect(status().isBadRequest());

        List<ProjectParticipant> projectParticipantList = projectParticipantRepository.findAll();
        assertThat(projectParticipantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectParticipantRepository.findAll().size();
        // set the field null
        projectParticipant.setStatus(null);

        // Create the ProjectParticipant, which fails.

        restProjectParticipantMockMvc.perform(post("/api/project-participants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectParticipant)))
            .andExpect(status().isBadRequest());

        List<ProjectParticipant> projectParticipantList = projectParticipantRepository.findAll();
        assertThat(projectParticipantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProjectParticipants() throws Exception {
        // Initialize the database
        projectParticipantRepository.saveAndFlush(projectParticipant);

        // Get all the projectParticipantList
        restProjectParticipantMockMvc.perform(get("/api/project-participants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectParticipant.getId().intValue())))
            .andExpect(jsonPath("$.[*].memberId").value(hasItem(DEFAULT_MEMBER_ID.intValue())))
            .andExpect(jsonPath("$.[*].inviterId").value(hasItem(DEFAULT_INVITER_ID.intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS)))
            .andExpect(jsonPath("$.[*].relationshipToInviter").value(hasItem(DEFAULT_RELATIONSHIP_TO_INVITER)))
            .andExpect(jsonPath("$.[*].permission").value(hasItem(DEFAULT_PERMISSION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].statusTLM").value(hasItem(DEFAULT_STATUS_TLM.toString())))
            .andExpect(jsonPath("$.[*].whenCreated").value(hasItem(DEFAULT_WHEN_CREATED.toString())));
    }
    
    @Test
    @Transactional
    public void getProjectParticipant() throws Exception {
        // Initialize the database
        projectParticipantRepository.saveAndFlush(projectParticipant);

        // Get the projectParticipant
        restProjectParticipantMockMvc.perform(get("/api/project-participants/{id}", projectParticipant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projectParticipant.getId().intValue()))
            .andExpect(jsonPath("$.memberId").value(DEFAULT_MEMBER_ID.intValue()))
            .andExpect(jsonPath("$.inviterId").value(DEFAULT_INVITER_ID.intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS))
            .andExpect(jsonPath("$.relationshipToInviter").value(DEFAULT_RELATIONSHIP_TO_INVITER))
            .andExpect(jsonPath("$.permission").value(DEFAULT_PERMISSION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.statusTLM").value(DEFAULT_STATUS_TLM.toString()))
            .andExpect(jsonPath("$.whenCreated").value(DEFAULT_WHEN_CREATED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProjectParticipant() throws Exception {
        // Get the projectParticipant
        restProjectParticipantMockMvc.perform(get("/api/project-participants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectParticipant() throws Exception {
        // Initialize the database
        projectParticipantService.save(projectParticipant);

        int databaseSizeBeforeUpdate = projectParticipantRepository.findAll().size();

        // Update the projectParticipant
        ProjectParticipant updatedProjectParticipant = projectParticipantRepository.findById(projectParticipant.getId()).get();
        // Disconnect from session so that the updates on updatedProjectParticipant are not directly saved in db
        em.detach(updatedProjectParticipant);
        updatedProjectParticipant
            .memberId(UPDATED_MEMBER_ID)
            .inviterId(UPDATED_INVITER_ID)
            .firstName(UPDATED_FIRST_NAME)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .relationshipToInviter(UPDATED_RELATIONSHIP_TO_INVITER)
            .permission(UPDATED_PERMISSION)
            .status(UPDATED_STATUS)
            .statusTLM(UPDATED_STATUS_TLM)
            .whenCreated(UPDATED_WHEN_CREATED);

        restProjectParticipantMockMvc.perform(put("/api/project-participants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjectParticipant)))
            .andExpect(status().isOk());

        // Validate the ProjectParticipant in the database
        List<ProjectParticipant> projectParticipantList = projectParticipantRepository.findAll();
        assertThat(projectParticipantList).hasSize(databaseSizeBeforeUpdate);
        ProjectParticipant testProjectParticipant = projectParticipantList.get(projectParticipantList.size() - 1);
        assertThat(testProjectParticipant.getMemberId()).isEqualTo(UPDATED_MEMBER_ID);
        assertThat(testProjectParticipant.getInviterId()).isEqualTo(UPDATED_INVITER_ID);
        assertThat(testProjectParticipant.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testProjectParticipant.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testProjectParticipant.getRelationshipToInviter()).isEqualTo(UPDATED_RELATIONSHIP_TO_INVITER);
        assertThat(testProjectParticipant.getPermission()).isEqualTo(UPDATED_PERMISSION);
        assertThat(testProjectParticipant.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProjectParticipant.getStatusTLM()).isEqualTo(UPDATED_STATUS_TLM);
        assertThat(testProjectParticipant.getWhenCreated()).isEqualTo(UPDATED_WHEN_CREATED);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectParticipant() throws Exception {
        int databaseSizeBeforeUpdate = projectParticipantRepository.findAll().size();

        // Create the ProjectParticipant

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectParticipantMockMvc.perform(put("/api/project-participants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectParticipant)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectParticipant in the database
        List<ProjectParticipant> projectParticipantList = projectParticipantRepository.findAll();
        assertThat(projectParticipantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProjectParticipant() throws Exception {
        // Initialize the database
        projectParticipantService.save(projectParticipant);

        int databaseSizeBeforeDelete = projectParticipantRepository.findAll().size();

        // Delete the projectParticipant
        restProjectParticipantMockMvc.perform(delete("/api/project-participants/{id}", projectParticipant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectParticipant> projectParticipantList = projectParticipantRepository.findAll();
        assertThat(projectParticipantList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectParticipant.class);
        ProjectParticipant projectParticipant1 = new ProjectParticipant();
        projectParticipant1.setId(1L);
        ProjectParticipant projectParticipant2 = new ProjectParticipant();
        projectParticipant2.setId(projectParticipant1.getId());
        assertThat(projectParticipant1).isEqualTo(projectParticipant2);
        projectParticipant2.setId(2L);
        assertThat(projectParticipant1).isNotEqualTo(projectParticipant2);
        projectParticipant1.setId(null);
        assertThat(projectParticipant1).isNotEqualTo(projectParticipant2);
    }
}
