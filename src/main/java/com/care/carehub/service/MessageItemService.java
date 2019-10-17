package com.care.carehub.service;

import com.care.carehub.domain.MessageItem;
import com.care.carehub.repository.MessageItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MessageItem}.
 */
@Service
@Transactional
public class MessageItemService {

    private final Logger log = LoggerFactory.getLogger(MessageItemService.class);

    private final MessageItemRepository messageItemRepository;

    public MessageItemService(MessageItemRepository messageItemRepository) {
        this.messageItemRepository = messageItemRepository;
    }

    /**
     * Save a messageItem.
     *
     * @param messageItem the entity to save.
     * @return the persisted entity.
     */
    public MessageItem save(MessageItem messageItem) {
        log.debug("Request to save MessageItem : {}", messageItem);
        return messageItemRepository.save(messageItem);
    }

    /**
     * Get all the messageItems.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MessageItem> findAll() {
        log.debug("Request to get all MessageItems");
        return messageItemRepository.findAll();
    }


    /**
     * Get one messageItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MessageItem> findOne(Long id) {
        log.debug("Request to get MessageItem : {}", id);
        return messageItemRepository.findById(id);
    }

    /**
     * Delete the messageItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MessageItem : {}", id);
        messageItemRepository.deleteById(id);
    }
}
