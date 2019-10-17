package com.care.carehub.web.rest;

import com.care.carehub.domain.MessageItem;
import com.care.carehub.service.MessageItemService;
import com.care.carehub.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.care.carehub.domain.MessageItem}.
 */
@RestController
@RequestMapping("/api")
public class MessageItemResource {

    private final Logger log = LoggerFactory.getLogger(MessageItemResource.class);

    private static final String ENTITY_NAME = "messageItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MessageItemService messageItemService;

    public MessageItemResource(MessageItemService messageItemService) {
        this.messageItemService = messageItemService;
    }

    /**
     * {@code POST  /message-items} : Create a new messageItem.
     *
     * @param messageItem the messageItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new messageItem, or with status {@code 400 (Bad Request)} if the messageItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/message-items")
    public ResponseEntity<MessageItem> createMessageItem(@RequestBody MessageItem messageItem) throws URISyntaxException {
        log.debug("REST request to save MessageItem : {}", messageItem);
        if (messageItem.getId() != null) {
            throw new BadRequestAlertException("A new messageItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MessageItem result = messageItemService.save(messageItem);
        return ResponseEntity.created(new URI("/api/message-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /message-items} : Updates an existing messageItem.
     *
     * @param messageItem the messageItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated messageItem,
     * or with status {@code 400 (Bad Request)} if the messageItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the messageItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/message-items")
    public ResponseEntity<MessageItem> updateMessageItem(@RequestBody MessageItem messageItem) throws URISyntaxException {
        log.debug("REST request to update MessageItem : {}", messageItem);
        if (messageItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MessageItem result = messageItemService.save(messageItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, messageItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /message-items} : get all the messageItems.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of messageItems in body.
     */
    @GetMapping("/message-items")
    public List<MessageItem> getAllMessageItems() {
        log.debug("REST request to get all MessageItems");
        return messageItemService.findAll();
    }

    /**
     * {@code GET  /message-items/:id} : get the "id" messageItem.
     *
     * @param id the id of the messageItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the messageItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/message-items/{id}")
    public ResponseEntity<MessageItem> getMessageItem(@PathVariable Long id) {
        log.debug("REST request to get MessageItem : {}", id);
        Optional<MessageItem> messageItem = messageItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(messageItem);
    }

    /**
     * {@code DELETE  /message-items/:id} : delete the "id" messageItem.
     *
     * @param id the id of the messageItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/message-items/{id}")
    public ResponseEntity<Void> deleteMessageItem(@PathVariable Long id) {
        log.debug("REST request to delete MessageItem : {}", id);
        messageItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
