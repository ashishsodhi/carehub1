package com.care.carehub.web.rest;

import com.care.carehub.domain.ProjectTemplate;
import com.care.carehub.service.ProjectTemplateService;
import com.care.carehub.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.care.carehub.domain.ProjectTemplate}.
 */
@RestController
@RequestMapping("/api")
public class ProjectTemplateResource {

    private final Logger log = LoggerFactory.getLogger(ProjectTemplateResource.class);

    private static final String ENTITY_NAME = "projectTemplate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectTemplateService projectTemplateService;

    public ProjectTemplateResource(ProjectTemplateService projectTemplateService) {
        this.projectTemplateService = projectTemplateService;
    }

    /**
     * {@code POST  /project-templates} : Create a new projectTemplate.
     *
     * @param projectTemplate the projectTemplate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectTemplate, or with status {@code 400 (Bad Request)} if the projectTemplate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-templates")
    public ResponseEntity<ProjectTemplate> createProjectTemplate(@Valid @RequestBody ProjectTemplate projectTemplate) throws URISyntaxException {
        log.debug("REST request to save ProjectTemplate : {}", projectTemplate);
        if (projectTemplate.getId() != null) {
            throw new BadRequestAlertException("A new projectTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectTemplate result = projectTemplateService.save(projectTemplate);
        return ResponseEntity.created(new URI("/api/project-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-templates} : Updates an existing projectTemplate.
     *
     * @param projectTemplate the projectTemplate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectTemplate,
     * or with status {@code 400 (Bad Request)} if the projectTemplate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectTemplate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-templates")
    public ResponseEntity<ProjectTemplate> updateProjectTemplate(@Valid @RequestBody ProjectTemplate projectTemplate) throws URISyntaxException {
        log.debug("REST request to update ProjectTemplate : {}", projectTemplate);
        if (projectTemplate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectTemplate result = projectTemplateService.save(projectTemplate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, projectTemplate.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-templates} : get all the projectTemplates.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectTemplates in body.
     */
    @GetMapping("/project-templates")
    public List<ProjectTemplate> getAllProjectTemplates() {
        log.debug("REST request to get all ProjectTemplates");
        return projectTemplateService.findAll();
    }

    /**
     * {@code GET  /project-templates/:id} : get the "id" projectTemplate.
     *
     * @param id the id of the projectTemplate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectTemplate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-templates/{id}")
    public ResponseEntity<ProjectTemplate> getProjectTemplate(@PathVariable Long id) {
        log.debug("REST request to get ProjectTemplate : {}", id);
        Optional<ProjectTemplate> projectTemplate = projectTemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectTemplate);
    }

    /**
     * {@code DELETE  /project-templates/:id} : delete the "id" projectTemplate.
     *
     * @param id the id of the projectTemplate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-templates/{id}")
    public ResponseEntity<Void> deleteProjectTemplate(@PathVariable Long id) {
        log.debug("REST request to delete ProjectTemplate : {}", id);
        projectTemplateService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
