package com.care.carehub.repository;
import com.care.carehub.domain.Concerns;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Concerns entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConcernsRepository extends JpaRepository<Concerns, Long> {

}
