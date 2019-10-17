package com.care.carehub.web.rest;

import com.care.carehub.domain.ProjectParticipant;
import com.care.carehub.service.ProjectParticipantService;
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
 * REST controller for managing {@link com.care.carehub.domain.ProjectParticipant}.
 */
@RestController
@RequestMapping("/api")
public class ProjectParticipantResource {

    private final Logger log = LoggerFactory.getLogger(ProjectParticipantResource.class);

    private static final String ENTITY_NAME = "projectParticipant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectParticipantService projectParticipantService;

    public ProjectParticipantResource(ProjectParticipantService projectParticipantService) {
        this.projectParticipantService = projectParticipantService;
    }

    /**
     * {@code POST  /project-participants} : Create a new projectParticipant.
     *
     * @param projectParticipant the projectParticipant to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectParticipant, or with status {@code 400 (Bad Request)} if the projectParticipant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-participants")
    public ResponseEntity<ProjectParticipant> createProjectParticipant(@Valid @RequestBody ProjectParticipant projectParticipant) throws URISyntaxException {
        log.debug("REST request to save ProjectParticipant : {}", projectParticipant);
        if (projectParticipant.getId() != null) {
            throw new BadRequestAlertException("A new projectParticipant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectParticipant result = projectParticipantService.save(projectParticipant);
        return ResponseEntity.created(new URI("/api/project-participants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-participants} : Updates an existing projectParticipant.
     *
     * @param projectParticipant the projectParticipant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectParticipant,
     * or with status {@code 400 (Bad Request)} if the projectParticipant is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectParticipant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-participants")
    public ResponseEntity<ProjectParticipant> updateProjectParticipant(@Valid @RequestBody ProjectParticipant projectParticipant) throws URISyntaxException {
        log.debug("REST request to update ProjectParticipant : {}", projectParticipant);
        if (projectParticipant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectParticipant result = projectParticipantService.save(projectParticipant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, projectParticipant.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-participants} : get all the projectParticipants.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectParticipants in body.
     */
    @GetMapping("/project-participants")
    public List<ProjectParticipant> getAllProjectParticipants() {
        log.debug("REST request to get all ProjectParticipants");
        return projectParticipantService.findAll();
    }

    /**
     * {@code GET  /project-participants/:id} : get the "id" projectParticipant.
     *
     * @param id the id of the projectParticipant to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectParticipant, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-participants/{id}")
    public ResponseEntity<ProjectParticipant> getProjectParticipant(@PathVariable Long id) {
        log.debug("REST request to get ProjectParticipant : {}", id);
        Optional<ProjectParticipant> projectParticipant = projectParticipantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectParticipant);
    }

    /**
     * {@code DELETE  /project-participants/:id} : delete the "id" projectParticipant.
     *
     * @param id the id of the projectParticipant to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-participants/{id}")
    public ResponseEntity<Void> deleteProjectParticipant(@PathVariable Long id) {
        log.debug("REST request to delete ProjectParticipant : {}", id);
        projectParticipantService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
