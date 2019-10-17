package com.care.carehub.service;

import com.care.carehub.domain.Responsibilities;
import com.care.carehub.repository.ResponsibilitiesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Responsibilities}.
 */
@Service
@Transactional
public class ResponsibilitiesService {

    private final Logger log = LoggerFactory.getLogger(ResponsibilitiesService.class);

    private final ResponsibilitiesRepository responsibilitiesRepository;

    public ResponsibilitiesService(ResponsibilitiesRepository responsibilitiesRepository) {
        this.responsibilitiesRepository = responsibilitiesRepository;
    }

    /**
     * Save a responsibilities.
     *
     * @param responsibilities the entity to save.
     * @return the persisted entity.
     */
    public Responsibilities save(Responsibilities responsibilities) {
        log.debug("Request to save Responsibilities : {}", responsibilities);
        return responsibilitiesRepository.save(responsibilities);
    }

    /**
     * Get all the responsibilities.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Responsibilities> findAll() {
        log.debug("Request to get all Responsibilities");
        return responsibilitiesRepository.findAll();
    }


    /**
     * Get one responsibilities by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Responsibilities> findOne(Long id) {
        log.debug("Request to get Responsibilities : {}", id);
        return responsibilitiesRepository.findById(id);
    }

    /**
     * Delete the responsibilities by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Responsibilities : {}", id);
        responsibilitiesRepository.deleteById(id);
    }
}
