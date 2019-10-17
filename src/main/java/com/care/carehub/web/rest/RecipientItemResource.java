package com.care.carehub.web.rest;

import com.care.carehub.domain.RecipientItem;
import com.care.carehub.service.RecipientItemService;
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
 * REST controller for managing {@link com.care.carehub.domain.RecipientItem}.
 */
@RestController
@RequestMapping("/api")
public class RecipientItemResource {

    private final Logger log = LoggerFactory.getLogger(RecipientItemResource.class);

    private static final String ENTITY_NAME = "recipientItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecipientItemService recipientItemService;

    public RecipientItemResource(RecipientItemService recipientItemService) {
        this.recipientItemService = recipientItemService;
    }

    /**
     * {@code POST  /recipient-items} : Create a new recipientItem.
     *
     * @param recipientItem the recipientItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recipientItem, or with status {@code 400 (Bad Request)} if the recipientItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recipient-items")
    public ResponseEntity<RecipientItem> createRecipientItem(@Valid @RequestBody RecipientItem recipientItem) throws URISyntaxException {
        log.debug("REST request to save RecipientItem : {}", recipientItem);
        if (recipientItem.getId() != null) {
            throw new BadRequestAlertException("A new recipientItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecipientItem result = recipientItemService.save(recipientItem);
        return ResponseEntity.created(new URI("/api/recipient-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recipient-items} : Updates an existing recipientItem.
     *
     * @param recipientItem the recipientItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recipientItem,
     * or with status {@code 400 (Bad Request)} if the recipientItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recipientItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recipient-items")
    public ResponseEntity<RecipientItem> updateRecipientItem(@Valid @RequestBody RecipientItem recipientItem) throws URISyntaxException {
        log.debug("REST request to update RecipientItem : {}", recipientItem);
        if (recipientItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RecipientItem result = recipientItemService.save(recipientItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, recipientItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /recipient-items} : get all the recipientItems.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recipientItems in body.
     */
    @GetMapping("/recipient-items")
    public List<RecipientItem> getAllRecipientItems() {
        log.debug("REST request to get all RecipientItems");
        return recipientItemService.findAll();
    }

    /**
     * {@code GET  /recipient-items/:id} : get the "id" recipientItem.
     *
     * @param id the id of the recipientItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recipientItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recipient-items/{id}")
    public ResponseEntity<RecipientItem> getRecipientItem(@PathVariable Long id) {
        log.debug("REST request to get RecipientItem : {}", id);
        Optional<RecipientItem> recipientItem = recipientItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recipientItem);
    }

    /**
     * {@code DELETE  /recipient-items/:id} : delete the "id" recipientItem.
     *
     * @param id the id of the recipientItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recipient-items/{id}")
    public ResponseEntity<Void> deleteRecipientItem(@PathVariable Long id) {
        log.debug("REST request to delete RecipientItem : {}", id);
        recipientItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
