package com.care.carehub.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A MessageItem.
 */
@Entity
@Table(name = "message_item")
public class MessageItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "when_created")
    private Instant whenCreated;

    @OneToMany(mappedBy = "messageItem")
    private Set<RecipientItem> recipientItems = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("messageItems")
    private Message message;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getWhenCreated() {
        return whenCreated;
    }

    public MessageItem whenCreated(Instant whenCreated) {
        this.whenCreated = whenCreated;
        return this;
    }

    public void setWhenCreated(Instant whenCreated) {
        this.whenCreated = whenCreated;
    }

    public Set<RecipientItem> getRecipientItems() {
        return recipientItems;
    }

    public MessageItem recipientItems(Set<RecipientItem> recipientItems) {
        this.recipientItems = recipientItems;
        return this;
    }

    public MessageItem addRecipientItem(RecipientItem recipientItem) {
        this.recipientItems.add(recipientItem);
        recipientItem.setMessageItem(this);
        return this;
    }

    public MessageItem removeRecipientItem(RecipientItem recipientItem) {
        this.recipientItems.remove(recipientItem);
        recipientItem.setMessageItem(null);
        return this;
    }

    public void setRecipientItems(Set<RecipientItem> recipientItems) {
        this.recipientItems = recipientItems;
    }

    public Message getMessage() {
        return message;
    }

    public MessageItem message(Message message) {
        this.message = message;
        return this;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MessageItem)) {
            return false;
        }
        return id != null && id.equals(((MessageItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MessageItem{" +
            "id=" + getId() +
            ", whenCreated='" + getWhenCreated() + "'" +
            "}";
    }
}
