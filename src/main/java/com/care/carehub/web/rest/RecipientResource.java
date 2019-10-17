package com.care.carehub.web.rest;

import com.care.carehub.domain.Recipient;
import com.care.carehub.service.RecipientService;
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
 * REST controller for managing {@link com.care.carehub.domain.Recipient}.
 */
@RestController
@RequestMapping("/api")
public class RecipientResource {

    private final Logger log = LoggerFactory.getLogger(RecipientResource.class);

    private static final String ENTITY_NAME = "recipient";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecipientService recipientService;

    public RecipientResource(RecipientService recipientService) {
        this.recipientService = recipientService;
    }

    /**
     * {@code POST  /recipients} : Create a new recipient.
     *
     * @param recipient the recipient to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recipient, or with status {@code 400 (Bad Request)} if the recipient has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recipients")
    public ResponseEntity<Recipient> createRecipient(@Valid @RequestBody Recipient recipient) throws URISyntaxException {
        log.debug("REST request to save Recipient : {}", recipient);
        if (recipient.getId() != null) {
            throw new BadRequestAlertException("A new recipient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Recipient result = recipientService.save(recipient);
        return ResponseEntity.created(new URI("/api/recipients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recipients} : Updates an existing recipient.
     *
     * @param recipient the recipient to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recipient,
     * or with status {@code 400 (Bad Request)} if the recipient is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recipient couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recipients")
    public ResponseEntity<Recipient> updateRecipient(@Valid @RequestBody Recipient recipient) throws URISyntaxException {
        log.debug("REST request to update Recipient : {}", recipient);
        if (recipient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Recipient result = recipientService.save(recipient);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, recipient.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /recipients} : get all the recipients.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recipients in body.
     */
    @GetMapping("/recipients")
    public List<Recipient> getAllRecipients() {
        log.debug("REST request to get all Recipients");
        return recipientService.findAll();
    }

    /**
     * {@code GET  /recipients/:id} : get the "id" recipient.
     *
     * @param id the id of the recipient to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recipient, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recipients/{id}")
    public ResponseEntity<Recipient> getRecipient(@PathVariable Long id) {
        log.debug("REST request to get Recipient : {}", id);
        Optional<Recipient> recipient = recipientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recipient);
    }

    /**
     * {@code DELETE  /recipients/:id} : delete the "id" recipient.
     *
     * @param id the id of the recipient to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recipients/{id}")
    public ResponseEntity<Void> deleteRecipient(@PathVariable Long id) {
        log.debug("REST request to delete Recipient : {}", id);
        recipientService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
