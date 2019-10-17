package com.care.carehub.service;

import com.care.carehub.domain.Recipient;
import com.care.carehub.repository.RecipientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Recipient}.
 */
@Service
@Transactional
public class RecipientService {

    private final Logger log = LoggerFactory.getLogger(RecipientService.class);

    private final RecipientRepository recipientRepository;

    public RecipientService(RecipientRepository recipientRepository) {
        this.recipientRepository = recipientRepository;
    }

    /**
     * Save a recipient.
     *
     * @param recipient the entity to save.
     * @return the persisted entity.
     */
    public Recipient save(Recipient recipient) {
        log.debug("Request to save Recipient : {}", recipient);
        return recipientRepository.save(recipient);
    }

    /**
     * Get all the recipients.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Recipient> findAll() {
        log.debug("Request to get all Recipients");
        return recipientRepository.findAll();
    }


    /**
     * Get one recipient by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Recipient> findOne(Long id) {
        log.debug("Request to get Recipient : {}", id);
        return recipientRepository.findById(id);
    }

    /**
     * Delete the recipient by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Recipient : {}", id);
        recipientRepository.deleteById(id);
    }
}
