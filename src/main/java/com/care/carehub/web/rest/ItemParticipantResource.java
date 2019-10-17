package com.care.carehub.web.rest;

import com.care.carehub.domain.ItemParticipant;
import com.care.carehub.service.ItemParticipantService;
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
 * REST controller for managing {@link com.care.carehub.domain.ItemParticipant}.
 */
@RestController
@RequestMapping("/api")
public class ItemParticipantResource {

    private final Logger log = LoggerFactory.getLogger(ItemParticipantResource.class);

    private static final String ENTITY_NAME = "itemParticipant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemParticipantService itemParticipantService;

    public ItemParticipantResource(ItemParticipantService itemParticipantService) {
        this.itemParticipantService = itemParticipantService;
    }

    /**
     * {@code POST  /item-participants} : Create a new itemParticipant.
     *
     * @param itemParticipant the itemParticipant to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemParticipant, or with status {@code 400 (Bad Request)} if the itemParticipant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/item-participants")
    public ResponseEntity<ItemParticipant> createItemParticipant(@Valid @RequestBody ItemParticipant itemParticipant) throws URISyntaxException {
        log.debug("REST request to save ItemParticipant : {}", itemParticipant);
        if (itemParticipant.getId() != null) {
            throw new BadRequestAlertException("A new itemParticipant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemParticipant result = itemParticipantService.save(itemParticipant);
        return ResponseEntity.created(new URI("/api/item-participants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /item-participants} : Updates an existing itemParticipant.
     *
     * @param itemParticipant the itemParticipant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemParticipant,
     * or with status {@code 400 (Bad Request)} if the itemParticipant is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemParticipant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/item-participants")
    public ResponseEntity<ItemParticipant> updateItemParticipant(@Valid @RequestBody ItemParticipant itemParticipant) throws URISyntaxException {
        log.debug("REST request to update ItemParticipant : {}", itemParticipant);
        if (itemParticipant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemParticipant result = itemParticipantService.save(itemParticipant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, itemParticipant.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /item-participants} : get all the itemParticipants.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itemParticipants in body.
     */
    @GetMapping("/item-participants")
    public List<ItemParticipant> getAllItemParticipants() {
        log.debug("REST request to get all ItemParticipants");
        return itemParticipantService.findAll();
    }

    /**
     * {@code GET  /item-participants/:id} : get the "id" itemParticipant.
     *
     * @param id the id of the itemParticipant to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemParticipant, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/item-participants/{id}")
    public ResponseEntity<ItemParticipant> getItemParticipant(@PathVariable Long id) {
        log.debug("REST request to get ItemParticipant : {}", id);
        Optional<ItemParticipant> itemParticipant = itemParticipantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemParticipant);
    }

    /**
     * {@code DELETE  /item-participants/:id} : delete the "id" itemParticipant.
     *
     * @param id the id of the itemParticipant to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/item-participants/{id}")
    public ResponseEntity<Void> deleteItemParticipant(@PathVariable Long id) {
        log.debug("REST request to delete ItemParticipant : {}", id);
        itemParticipantService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
