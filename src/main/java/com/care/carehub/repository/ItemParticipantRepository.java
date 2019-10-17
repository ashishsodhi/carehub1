package com.care.carehub.repository;
import com.care.carehub.domain.ItemParticipant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ItemParticipant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemParticipantRepository extends JpaRepository<ItemParticipant, Long> {

}
