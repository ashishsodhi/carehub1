package com.care.carehub.repository;
import com.care.carehub.domain.ProjectTemplate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProjectTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectTemplateRepository extends JpaRepository<ProjectTemplate, Long> {

}
