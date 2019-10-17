package com.care.carehub.service;

import com.care.carehub.domain.RecipientItem;
import com.care.carehub.repository.RecipientItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link RecipientItem}.
 */
@Service
@Transactional
public class RecipientItemService {

    private final Logger log = LoggerFactory.getLogger(RecipientItemService.class);

    private final RecipientItemRepository recipientItemRepository;

    public RecipientItemService(RecipientItemRepository recipientItemRepository) {
        this.recipientItemRepository = recipientItemRepository;
    }

    /**
     * Save a recipientItem.
     *
     * @param recipientItem the entity to save.
     * @return the persisted entity.
     */
    public RecipientItem save(RecipientItem recipientItem) {
        log.debug("Request to save RecipientItem : {}", recipientItem);
        return recipientItemRepository.save(recipientItem);
    }

    /**
     * Get all the recipientItems.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<RecipientItem> findAll() {
        log.debug("Request to get all RecipientItems");
        return recipientItemRepository.findAll();
    }


    /**
     * Get one recipientItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RecipientItem> findOne(Long id) {
        log.debug("Request to get RecipientItem : {}", id);
        return recipientItemRepository.findById(id);
    }

    /**
     * Delete the recipientItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RecipientItem : {}", id);
        recipientItemRepository.deleteById(id);
    }
}
