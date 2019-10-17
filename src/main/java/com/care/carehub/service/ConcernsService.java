package com.care.carehub.service;

import com.care.carehub.domain.Concerns;
import com.care.carehub.repository.ConcernsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Concerns}.
 */
@Service
@Transactional
public class ConcernsService {

    private final Logger log = LoggerFactory.getLogger(ConcernsService.class);

    private final ConcernsRepository concernsRepository;

    public ConcernsService(ConcernsRepository concernsRepository) {
        this.concernsRepository = concernsRepository;
    }

    /**
     * Save a concerns.
     *
     * @param concerns the entity to save.
     * @return the persisted entity.
     */
    public Concerns save(Concerns concerns) {
        log.debug("Request to save Concerns : {}", concerns);
        return concernsRepository.save(concerns);
    }

    /**
     * Get all the concerns.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Concerns> findAll() {
        log.debug("Request to get all Concerns");
        return concernsRepository.findAll();
    }


    /**
     * Get one concerns by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Concerns> findOne(Long id) {
        log.debug("Request to get Concerns : {}", id);
        return concernsRepository.findById(id);
    }

    /**
     * Delete the concerns by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Concerns : {}", id);
        concernsRepository.deleteById(id);
    }
}
