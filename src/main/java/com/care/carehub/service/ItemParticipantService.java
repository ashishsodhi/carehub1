package com.care.carehub.service;

import com.care.carehub.domain.ItemParticipant;
import com.care.carehub.repository.ItemParticipantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ItemParticipant}.
 */
@Service
@Transactional
public class ItemParticipantService {

    private final Logger log = LoggerFactory.getLogger(ItemParticipantService.class);

    private final ItemParticipantRepository itemParticipantRepository;

    public ItemParticipantService(ItemParticipantRepository itemParticipantRepository) {
        this.itemParticipantRepository = itemParticipantRepository;
    }

    /**
     * Save a itemParticipant.
     *
     * @param itemParticipant the entity to save.
     * @return the persisted entity.
     */
    public ItemParticipant save(ItemParticipant itemParticipant) {
        log.debug("Request to save ItemParticipant : {}", itemParticipant);
        return itemParticipantRepository.save(itemParticipant);
    }

    /**
     * Get all the itemParticipants.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ItemParticipant> findAll() {
        log.debug("Request to get all ItemParticipants");
        return itemParticipantRepository.findAll();
    }


    /**
     * Get one itemParticipant by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ItemParticipant> findOne(Long id) {
        log.debug("Request to get ItemParticipant : {}", id);
        return itemParticipantRepository.findById(id);
    }

    /**
     * Delete the itemParticipant by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ItemParticipant : {}", id);
        itemParticipantRepository.deleteById(id);
    }
}
