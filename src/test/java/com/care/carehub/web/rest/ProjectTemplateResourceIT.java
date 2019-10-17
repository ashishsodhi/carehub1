package com.care.carehub.web.rest;

import com.care.carehub.CarehubApp;
import com.care.carehub.domain.ProjectTemplate;
import com.care.carehub.repository.ProjectTemplateRepository;
import com.care.carehub.service.ProjectTemplateService;
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
import java.util.List;

import static com.care.carehub.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProjectTemplateResource} REST controller.
 */
@SpringBootTest(classes = CarehubApp.class)
public class ProjectTemplateResourceIT {

    private static final Long DEFAULT_SERVICE_ID = 1L;
    private static final Long UPDATED_SERVICE_ID = 2L;

    private static final String DEFAULT_TEMPLATE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPLATE_CREATION_CLASS = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE_CREATION_CLASS = "BBBBBBBBBB";

    private static final String DEFAULT_WHEN_CREATED = "AAAAAAAAAA";
    private static final String UPDATED_WHEN_CREATED = "BBBBBBBBBB";

    @Autowired
    private ProjectTemplateRepository projectTemplateRepository;

    @Autowired
    private ProjectTemplateService projectTemplateService;

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

    private MockMvc restProjectTemplateMockMvc;

    private ProjectTemplate projectTemplate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjectTemplateResource projectTemplateResource = new ProjectTemplateResource(projectTemplateService);
        this.restProjectTemplateMockMvc = MockMvcBuilders.standaloneSetup(projectTemplateResource)
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
    public static ProjectTemplate createEntity(EntityManager em) {
        ProjectTemplate projectTemplate = new ProjectTemplate()
            .serviceId(DEFAULT_SERVICE_ID)
            .templateDescription(DEFAULT_TEMPLATE_DESCRIPTION)
            .templateCreationClass(DEFAULT_TEMPLATE_CREATION_CLASS)
            .whenCreated(DEFAULT_WHEN_CREATED);
        return projectTemplate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectTemplate createUpdatedEntity(EntityManager em) {
        ProjectTemplate projectTemplate = new ProjectTemplate()
            .serviceId(UPDATED_SERVICE_ID)
            .templateDescription(UPDATED_TEMPLATE_DESCRIPTION)
            .templateCreationClass(UPDATED_TEMPLATE_CREATION_CLASS)
            .whenCreated(UPDATED_WHEN_CREATED);
        return projectTemplate;
    }

    @BeforeEach
    public void initTest() {
        projectTemplate = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectTemplate() throws Exception {
        int databaseSizeBeforeCreate = projectTemplateRepository.findAll().size();

        // Create the ProjectTemplate
        restProjectTemplateMockMvc.perform(post("/api/project-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectTemplate)))
            .andExpect(status().isCreated());

        // Validate the ProjectTemplate in the database
        List<ProjectTemplate> projectTemplateList = projectTemplateRepository.findAll();
        assertThat(projectTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectTemplate testProjectTemplate = projectTemplateList.get(projectTemplateList.size() - 1);
        assertThat(testProjectTemplate.getServiceId()).isEqualTo(DEFAULT_SERVICE_ID);
        assertThat(testProjectTemplate.getTemplateDescription()).isEqualTo(DEFAULT_TEMPLATE_DESCRIPTION);
        assertThat(testProjectTemplate.getTemplateCreationClass()).isEqualTo(DEFAULT_TEMPLATE_CREATION_CLASS);
        assertThat(testProjectTemplate.getWhenCreated()).isEqualTo(DEFAULT_WHEN_CREATED);
    }

    @Test
    @Transactional
    public void createProjectTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectTemplateRepository.findAll().size();

        // Create the ProjectTemplate with an existing ID
        projectTemplate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectTemplateMockMvc.perform(post("/api/project-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectTemplate)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectTemplate in the database
        List<ProjectTemplate> projectTemplateList = projectTemplateRepository.findAll();
        assertThat(projectTemplateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkServiceIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectTemplateRepository.findAll().size();
        // set the field null
        projectTemplate.setServiceId(null);

        // Create the ProjectTemplate, which fails.

        restProjectTemplateMockMvc.perform(post("/api/project-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectTemplate)))
            .andExpect(status().isBadRequest());

        List<ProjectTemplate> projectTemplateList = projectTemplateRepository.findAll();
        assertThat(projectTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTemplateDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectTemplateRepository.findAll().size();
        // set the field null
        projectTemplate.setTemplateDescription(null);

        // Create the ProjectTemplate, which fails.

        restProjectTemplateMockMvc.perform(post("/api/project-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectTemplate)))
            .andExpect(status().isBadRequest());

        List<ProjectTemplate> projectTemplateList = projectTemplateRepository.findAll();
        assertThat(projectTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTemplateCreationClassIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectTemplateRepository.findAll().size();
        // set the field null
        projectTemplate.setTemplateCreationClass(null);

        // Create the ProjectTemplate, which fails.

        restProjectTemplateMockMvc.perform(post("/api/project-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectTemplate)))
            .andExpect(status().isBadRequest());

        List<ProjectTemplate> projectTemplateList = projectTemplateRepository.findAll();
        assertThat(projectTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProjectTemplates() throws Exception {
        // Initialize the database
        projectTemplateRepository.saveAndFlush(projectTemplate);

        // Get all the projectTemplateList
        restProjectTemplateMockMvc.perform(get("/api/project-templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceId").value(hasItem(DEFAULT_SERVICE_ID.intValue())))
            .andExpect(jsonPath("$.[*].templateDescription").value(hasItem(DEFAULT_TEMPLATE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].templateCreationClass").value(hasItem(DEFAULT_TEMPLATE_CREATION_CLASS)))
            .andExpect(jsonPath("$.[*].whenCreated").value(hasItem(DEFAULT_WHEN_CREATED)));
    }
    
    @Test
    @Transactional
    public void getProjectTemplate() throws Exception {
        // Initialize the database
        projectTemplateRepository.saveAndFlush(projectTemplate);

        // Get the projectTemplate
        restProjectTemplateMockMvc.perform(get("/api/project-templates/{id}", projectTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projectTemplate.getId().intValue()))
            .andExpect(jsonPath("$.serviceId").value(DEFAULT_SERVICE_ID.intValue()))
            .andExpect(jsonPath("$.templateDescription").value(DEFAULT_TEMPLATE_DESCRIPTION))
            .andExpect(jsonPath("$.templateCreationClass").value(DEFAULT_TEMPLATE_CREATION_CLASS))
            .andExpect(jsonPath("$.whenCreated").value(DEFAULT_WHEN_CREATED));
    }

    @Test
    @Transactional
    public void getNonExistingProjectTemplate() throws Exception {
        // Get the projectTemplate
        restProjectTemplateMockMvc.perform(get("/api/project-templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectTemplate() throws Exception {
        // Initialize the database
        projectTemplateService.save(projectTemplate);

        int databaseSizeBeforeUpdate = projectTemplateRepository.findAll().size();

        // Update the projectTemplate
        ProjectTemplate updatedProjectTemplate = projectTemplateRepository.findById(projectTemplate.getId()).get();
        // Disconnect from session so that the updates on updatedProjectTemplate are not directly saved in db
        em.detach(updatedProjectTemplate);
        updatedProjectTemplate
            .serviceId(UPDATED_SERVICE_ID)
            .templateDescription(UPDATED_TEMPLATE_DESCRIPTION)
            .templateCreationClass(UPDATED_TEMPLATE_CREATION_CLASS)
            .whenCreated(UPDATED_WHEN_CREATED);

        restProjectTemplateMockMvc.perform(put("/api/project-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjectTemplate)))
            .andExpect(status().isOk());

        // Validate the ProjectTemplate in the database
        List<ProjectTemplate> projectTemplateList = projectTemplateRepository.findAll();
        assertThat(projectTemplateList).hasSize(databaseSizeBeforeUpdate);
        ProjectTemplate testProjectTemplate = projectTemplateList.get(projectTemplateList.size() - 1);
        assertThat(testProjectTemplate.getServiceId()).isEqualTo(UPDATED_SERVICE_ID);
        assertThat(testProjectTemplate.getTemplateDescription()).isEqualTo(UPDATED_TEMPLATE_DESCRIPTION);
        assertThat(testProjectTemplate.getTemplateCreationClass()).isEqualTo(UPDATED_TEMPLATE_CREATION_CLASS);
        assertThat(testProjectTemplate.getWhenCreated()).isEqualTo(UPDATED_WHEN_CREATED);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectTemplate() throws Exception {
        int databaseSizeBeforeUpdate = projectTemplateRepository.findAll().size();

        // Create the ProjectTemplate

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectTemplateMockMvc.perform(put("/api/project-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectTemplate)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectTemplate in the database
        List<ProjectTemplate> projectTemplateList = projectTemplateRepository.findAll();
        assertThat(projectTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProjectTemplate() throws Exception {
        // Initialize the database
        projectTemplateService.save(projectTemplate);

        int databaseSizeBeforeDelete = projectTemplateRepository.findAll().size();

        // Delete the projectTemplate
        restProjectTemplateMockMvc.perform(delete("/api/project-templates/{id}", projectTemplate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectTemplate> projectTemplateList = projectTemplateRepository.findAll();
        assertThat(projectTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectTemplate.class);
        ProjectTemplate projectTemplate1 = new ProjectTemplate();
        projectTemplate1.setId(1L);
        ProjectTemplate projectTemplate2 = new ProjectTemplate();
        projectTemplate2.setId(projectTemplate1.getId());
        assertThat(projectTemplate1).isEqualTo(projectTemplate2);
        projectTemplate2.setId(2L);
        assertThat(projectTemplate1).isNotEqualTo(projectTemplate2);
        projectTemplate1.setId(null);
        assertThat(projectTemplate1).isNotEqualTo(projectTemplate2);
    }
}
