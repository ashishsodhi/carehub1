package com.care.carehub.web.rest;

import com.care.carehub.domain.Concerns;
import com.care.carehub.service.ConcernsService;
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
 * REST controller for managing {@link com.care.carehub.domain.Concerns}.
 */
@RestController
@RequestMapping("/api")
public class ConcernsResource {

    private final Logger log = LoggerFactory.getLogger(ConcernsResource.class);

    private static final String ENTITY_NAME = "concerns";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConcernsService concernsService;

    public ConcernsResource(ConcernsService concernsService) {
        this.concernsService = concernsService;
    }

    /**
     * {@code POST  /concerns} : Create a new concerns.
     *
     * @param concerns the concerns to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new concerns, or with status {@code 400 (Bad Request)} if the concerns has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/concerns")
    public ResponseEntity<Concerns> createConcerns(@Valid @RequestBody Concerns concerns) throws URISyntaxException {
        log.debug("REST request to save Concerns : {}", concerns);
        if (concerns.getId() != null) {
            throw new BadRequestAlertException("A new concerns cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Concerns result = concernsService.save(concerns);
        return ResponseEntity.created(new URI("/api/concerns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /concerns} : Updates an existing concerns.
     *
     * @param concerns the concerns to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated concerns,
     * or with status {@code 400 (Bad Request)} if the concerns is not valid,
     * or with status {@code 500 (Internal Server Error)} if the concerns couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/concerns")
    public ResponseEntity<Concerns> updateConcerns(@Valid @RequestBody Concerns concerns) throws URISyntaxException {
        log.debug("REST request to update Concerns : {}", concerns);
        if (concerns.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Concerns result = concernsService.save(concerns);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, concerns.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /concerns} : get all the concerns.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of concerns in body.
     */
    @GetMapping("/concerns")
    public List<Concerns> getAllConcerns() {
        log.debug("REST request to get all Concerns");
        return concernsService.findAll();
    }

    /**
     * {@code GET  /concerns/:id} : get the "id" concerns.
     *
     * @param id the id of the concerns to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the concerns, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/concerns/{id}")
    public ResponseEntity<Concerns> getConcerns(@PathVariable Long id) {
        log.debug("REST request to get Concerns : {}", id);
        Optional<Concerns> concerns = concernsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(concerns);
    }

    /**
     * {@code DELETE  /concerns/:id} : delete the "id" concerns.
     *
     * @param id the id of the concerns to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/concerns/{id}")
    public ResponseEntity<Void> deleteConcerns(@PathVariable Long id) {
        log.debug("REST request to delete Concerns : {}", id);
        concernsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
