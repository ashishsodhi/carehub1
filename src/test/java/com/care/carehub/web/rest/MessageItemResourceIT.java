package com.care.carehub.web.rest;

import com.care.carehub.CarehubApp;
import com.care.carehub.domain.MessageItem;
import com.care.carehub.repository.MessageItemRepository;
import com.care.carehub.service.MessageItemService;
import com.care.carehub.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.care.carehub.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MessageItemResource} REST controller.
 */
@SpringBootTest(classes = CarehubApp.class)
public class MessageItemResourceIT {

    private static final Instant DEFAULT_WHEN_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_WHEN_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private MessageItemRepository messageItemRepository;

    @Autowired
    private MessageItemService messageItemService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restMessageItemMockMvc;

    private MessageItem messageItem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MessageItemResource messageItemResource = new MessageItemResource(messageItemService);
        this.restMessageItemMockMvc = MockMvcBuilders.standaloneSetup(messageItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MessageItem createEntity(EntityManager em) {
        MessageItem messageItem = new MessageItem()
            .whenCreated(DEFAULT_WHEN_CREATED);
        return messageItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MessageItem createUpdatedEntity(EntityManager em) {
        MessageItem messageItem = new MessageItem()
            .whenCreated(UPDATED_WHEN_CREATED);
        return messageItem;
    }

    @BeforeEach
    public void initTest() {
        messageItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createMessageItem() throws Exception {
        int databaseSizeBeforeCreate = messageItemRepository.findAll().size();

        // Create the MessageItem
        restMessageItemMockMvc.perform(post("/api/message-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageItem)))
            .andExpect(status().isCreated());

        // Validate the MessageItem in the database
        List<MessageItem> messageItemList = messageItemRepository.findAll();
        assertThat(messageItemList).hasSize(databaseSizeBeforeCreate + 1);
        MessageItem testMessageItem = messageItemList.get(messageItemList.size() - 1);
        assertThat(testMessageItem.getWhenCreated()).isEqualTo(DEFAULT_WHEN_CREATED);
    }

    @Test
    @Transactional
    public void createMessageItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = messageItemRepository.findAll().size();

        // Create the MessageItem with an existing ID
        messageItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMessageItemMockMvc.perform(post("/api/message-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageItem)))
            .andExpect(status().isBadRequest());

        // Validate the MessageItem in the database
        List<MessageItem> messageItemList = messageItemRepository.findAll();
        assertThat(messageItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMessageItems() throws Exception {
        // Initialize the database
        messageItemRepository.saveAndFlush(messageItem);

        // Get all the messageItemList
        restMessageItemMockMvc.perform(get("/api/message-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(messageItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].whenCreated").value(hasItem(DEFAULT_WHEN_CREATED.toString())));
    }
    
    @Test
    @Transactional
    public void getMessageItem() throws Exception {
        // Initialize the database
        messageItemRepository.saveAndFlush(messageItem);

        // Get the messageItem
        restMessageItemMockMvc.perform(get("/api/message-items/{id}", messageItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(messageItem.getId().intValue()))
            .andExpect(jsonPath("$.whenCreated").value(DEFAULT_WHEN_CREATED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMessageItem() throws Exception {
        // Get the messageItem
        restMessageItemMockMvc.perform(get("/api/message-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMessageItem() throws Exception {
        // Initialize the database
        messageItemService.save(messageItem);

        int databaseSizeBeforeUpdate = messageItemRepository.findAll().size();

        // Update the messageItem
        MessageItem updatedMessageItem = messageItemRepository.findById(messageItem.getId()).get();
        // Disconnect from session so that the updates on updatedMessageItem are not directly saved in db
        em.detach(updatedMessageItem);
        updatedMessageItem
            .whenCreated(UPDATED_WHEN_CREATED);

        restMessageItemMockMvc.perform(put("/api/message-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMessageItem)))
            .andExpect(status().isOk());

        // Validate the MessageItem in the database
        List<MessageItem> messageItemList = messageItemRepository.findAll();
        assertThat(messageItemList).hasSize(databaseSizeBeforeUpdate);
        MessageItem testMessageItem = messageItemList.get(messageItemList.size() - 1);
        assertThat(testMessageItem.getWhenCreated()).isEqualTo(UPDATED_WHEN_CREATED);
    }

    @Test
    @Transactional
    public void updateNonExistingMessageItem() throws Exception {
        int databaseSizeBeforeUpdate = messageItemRepository.findAll().size();

        // Create the MessageItem

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMessageItemMockMvc.perform(put("/api/message-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageItem)))
            .andExpect(status().isBadRequest());

        // Validate the MessageItem in the database
        List<MessageItem> messageItemList = messageItemRepository.findAll();
        assertThat(messageItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMessageItem() throws Exception {
        // Initialize the database
        messageItemService.save(messageItem);

        int databaseSizeBeforeDelete = messageItemRepository.findAll().size();

        // Delete the messageItem
        restMessageItemMockMvc.perform(delete("/api/message-items/{id}", messageItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MessageItem> messageItemList = messageItemRepository.findAll();
        assertThat(messageItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MessageItem.class);
        MessageItem messageItem1 = new MessageItem();
        messageItem1.setId(1L);
        MessageItem messageItem2 = new MessageItem();
        messageItem2.setId(messageItem1.getId());
        assertThat(messageItem1).isEqualTo(messageItem2);
        messageItem2.setId(2L);
        assertThat(messageItem1).isNotEqualTo(messageItem2);
        messageItem1.setId(null);
        assertThat(messageItem1).isNotEqualTo(messageItem2);
    }
}
