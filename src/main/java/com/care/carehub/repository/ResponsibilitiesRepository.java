package com.care.carehub.repository;
import com.care.carehub.domain.Responsibilities;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Responsibilities entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResponsibilitiesRepository extends JpaRepository<Responsibilities, Long> {

}
