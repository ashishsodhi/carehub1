package com.care.carehub.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A RecipientItem.
 */
@Entity
@Table(name = "recipient_item")
public class RecipientItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "permission_to_all", nullable = false)
    private Boolean permissionToAll;

    @Column(name = "when_created")
    private Instant whenCreated;

    @OneToOne
    @JoinColumn(unique = true)
    private Task task;

    @OneToOne
    @JoinColumn(unique = true)
    private Document document;

    @OneToMany(mappedBy = "recipientItem")
    private Set<ItemParticipant> itemParticipants = new HashSet<>();

    @OneToMany(mappedBy = "recipientItem")
    private Set<Message> messages = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("recipientItems")
    private Recipient recipient;

    @ManyToOne
    @JsonIgnoreProperties("recipientItems")
    private MessageItem messageItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isPermissionToAll() {
        return permissionToAll;
    }

    public RecipientItem permissionToAll(Boolean permissionToAll) {
        this.permissionToAll = permissionToAll;
        return this;
    }

    public void setPermissionToAll(Boolean permissionToAll) {
        this.permissionToAll = permissionToAll;
    }

    public Instant getWhenCreated() {
        return whenCreated;
    }

    public RecipientItem whenCreated(Instant whenCreated) {
        this.whenCreated = whenCreated;
        return this;
    }

    public void setWhenCreated(Instant whenCreated) {
        this.whenCreated = whenCreated;
    }

    public Task getTask() {
        return task;
    }

    public RecipientItem task(Task task) {
        this.task = task;
        return this;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Document getDocument() {
        return document;
    }

    public RecipientItem document(Document document) {
        this.document = document;
        return this;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Set<ItemParticipant> getItemParticipants() {
        return itemParticipants;
    }

    public RecipientItem itemParticipants(Set<ItemParticipant> itemParticipants) {
        this.itemParticipants = itemParticipants;
        return this;
    }

    public RecipientItem addItemParticipant(ItemParticipant itemParticipant) {
        this.itemParticipants.add(itemParticipant);
        itemParticipant.setRecipientItem(this);
        return this;
    }

    public RecipientItem removeItemParticipant(ItemParticipant itemParticipant) {
        this.itemParticipants.remove(itemParticipant);
        itemParticipant.setRecipientItem(null);
        return this;
    }

    public void setItemParticipants(Set<ItemParticipant> itemParticipants) {
        this.itemParticipants = itemParticipants;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public RecipientItem messages(Set<Message> messages) {
        this.messages = messages;
        return this;
    }

    public RecipientItem addMessage(Message message) {
        this.messages.add(message);
        message.setRecipientItem(this);
        return this;
    }

    public RecipientItem removeMessage(Message message) {
        this.messages.remove(message);
        message.setRecipientItem(null);
        return this;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public RecipientItem recipient(Recipient recipient) {
        this.recipient = recipient;
        return this;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public MessageItem getMessageItem() {
        return messageItem;
    }

    public RecipientItem messageItem(MessageItem messageItem) {
        this.messageItem = messageItem;
        return this;
    }

    public void setMessageItem(MessageItem messageItem) {
        this.messageItem = messageItem;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecipientItem)) {
            return false;
        }
        return id != null && id.equals(((RecipientItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RecipientItem{" +
            "id=" + getId() +
            ", permissionToAll='" + isPermissionToAll() + "'" +
            ", whenCreated='" + getWhenCreated() + "'" +
            "}";
    }
}
