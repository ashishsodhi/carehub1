package com.care.carehub.service;

import com.care.carehub.domain.ProjectTemplate;
import com.care.carehub.repository.ProjectTemplateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectTemplate}.
 */
@Service
@Transactional
public class ProjectTemplateService {

    private final Logger log = LoggerFactory.getLogger(ProjectTemplateService.class);

    private final ProjectTemplateRepository projectTemplateRepository;

    public ProjectTemplateService(ProjectTemplateRepository projectTemplateRepository) {
        this.projectTemplateRepository = projectTemplateRepository;
    }

    /**
     * Save a projectTemplate.
     *
     * @param projectTemplate the entity to save.
     * @return the persisted entity.
     */
    public ProjectTemplate save(ProjectTemplate projectTemplate) {
        log.debug("Request to save ProjectTemplate : {}", projectTemplate);
        return projectTemplateRepository.save(projectTemplate);
    }

    /**
     * Get all the projectTemplates.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectTemplate> findAll() {
        log.debug("Request to get all ProjectTemplates");
        return projectTemplateRepository.findAll();
    }


    /**
     * Get one projectTemplate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectTemplate> findOne(Long id) {
        log.debug("Request to get ProjectTemplate : {}", id);
        return projectTemplateRepository.findById(id);
    }

    /**
     * Delete the projectTemplate by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectTemplate : {}", id);
        projectTemplateRepository.deleteById(id);
    }
}
