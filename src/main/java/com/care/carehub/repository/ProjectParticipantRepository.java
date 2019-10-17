package com.care.carehub.repository;
import com.care.carehub.domain.ProjectParticipant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProjectParticipant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectParticipantRepository extends JpaRepository<ProjectParticipant, Long> {

}
