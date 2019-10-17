package com.care.carehub.repository;
import com.care.carehub.domain.MessageItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MessageItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MessageItemRepository extends JpaRepository<MessageItem, Long> {

}
