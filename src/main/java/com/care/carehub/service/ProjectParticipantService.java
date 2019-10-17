package com.care.carehub.service;

import com.care.carehub.domain.ProjectParticipant;
import com.care.carehub.repository.ProjectParticipantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectParticipant}.
 */
@Service
@Transactional
public class ProjectParticipantService {

    private final Logger log = LoggerFactory.getLogger(ProjectParticipantService.class);

    private final ProjectParticipantRepository projectParticipantRepository;

    public ProjectParticipantService(ProjectParticipantRepository projectParticipantRepository) {
        this.projectParticipantRepository = projectParticipantRepository;
    }

    /**
     * Save a projectParticipant.
     *
     * @param projectParticipant the entity to save.
     * @return the persisted entity.
     */
    public ProjectParticipant save(ProjectParticipant projectParticipant) {
        log.debug("Request to save ProjectParticipant : {}", projectParticipant);
        return projectParticipantRepository.save(projectParticipant);
    }

    /**
     * Get all the projectParticipants.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectParticipant> findAll() {
        log.debug("Request to get all ProjectParticipants");
        return projectParticipantRepository.findAll();
    }


    /**
     * Get one projectParticipant by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectParticipant> findOne(Long id) {
        log.debug("Request to get ProjectParticipant : {}", id);
        return projectParticipantRepository.findById(id);
    }

    /**
     * Delete the projectParticipant by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectParticipant : {}", id);
        projectParticipantRepository.deleteById(id);
    }
}
