package com.care.carehub.repository;
import com.care.carehub.domain.RecipientItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RecipientItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecipientItemRepository extends JpaRepository<RecipientItem, Long> {

}
