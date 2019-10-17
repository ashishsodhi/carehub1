package com.care.carehub.web.rest;

import com.care.carehub.domain.Responsibilities;
import com.care.carehub.service.ResponsibilitiesService;
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
 * REST controller for managing {@link com.care.carehub.domain.Responsibilities}.
 */
@RestController
@RequestMapping("/api")
public class ResponsibilitiesResource {

    private final Logger log = LoggerFactory.getLogger(ResponsibilitiesResource.class);

    private static final String ENTITY_NAME = "responsibilities";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResponsibilitiesService responsibilitiesService;

    public ResponsibilitiesResource(ResponsibilitiesService responsibilitiesService) {
        this.responsibilitiesService = responsibilitiesService;
    }

    /**
     * {@code POST  /responsibilities} : Create a new responsibilities.
     *
     * @param responsibilities the responsibilities to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new responsibilities, or with status {@code 400 (Bad Request)} if the responsibilities has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/responsibilities")
    public ResponseEntity<Responsibilities> createResponsibilities(@Valid @RequestBody Responsibilities responsibilities) throws URISyntaxException {
        log.debug("REST request to save Responsibilities : {}", responsibilities);
        if (responsibilities.getId() != null) {
            throw new BadRequestAlertException("A new responsibilities cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Responsibilities result = responsibilitiesService.save(responsibilities);
        return ResponseEntity.created(new URI("/api/responsibilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /responsibilities} : Updates an existing responsibilities.
     *
     * @param responsibilities the responsibilities to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated responsibilities,
     * or with status {@code 400 (Bad Request)} if the responsibilities is not valid,
     * or with status {@code 500 (Internal Server Error)} if the responsibilities couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/responsibilities")
    public ResponseEntity<Responsibilities> updateResponsibilities(@Valid @RequestBody Responsibilities responsibilities) throws URISyntaxException {
        log.debug("REST request to update Responsibilities : {}", responsibilities);
        if (responsibilities.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Responsibilities result = responsibilitiesService.save(responsibilities);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, responsibilities.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /responsibilities} : get all the responsibilities.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of responsibilities in body.
     */
    @GetMapping("/responsibilities")
    public List<Responsibilities> getAllResponsibilities() {
        log.debug("REST request to get all Responsibilities");
        return responsibilitiesService.findAll();
    }

    /**
     * {@code GET  /responsibilities/:id} : get the "id" responsibilities.
     *
     * @param id the id of the responsibilities to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the responsibilities, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/responsibilities/{id}")
    public ResponseEntity<Responsibilities> getResponsibilities(@PathVariable Long id) {
        log.debug("REST request to get Responsibilities : {}", id);
        Optional<Responsibilities> responsibilities = responsibilitiesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(responsibilities);
    }

    /**
     * {@code DELETE  /responsibilities/:id} : delete the "id" responsibilities.
     *
     * @param id the id of the responsibilities to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/responsibilities/{id}")
    public ResponseEntity<Void> deleteResponsibilities(@PathVariable Long id) {
        log.debug("REST request to delete Responsibilities : {}", id);
        responsibilitiesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
