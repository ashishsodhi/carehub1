package com.care.carehub.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Message.
 */
@Entity
@Table(name = "message")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recipient_id")
    private Long recipientId;

    @NotNull
    @Column(name = "posted_by_member_id", nullable = false)
    private Long postedByMemberId;

    @Column(name = "message_body")
    private String messageBody;

    @Column(name = "when_created")
    private Instant whenCreated;

    @OneToMany(mappedBy = "message")
    private Set<MessageItem> messageItems = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("messages")
    private Project project;

    @ManyToOne
    @JsonIgnoreProperties("messages")
    private RecipientItem recipientItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public Message recipientId(Long recipientId) {
        this.recipientId = recipientId;
        return this;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public Long getPostedByMemberId() {
        return postedByMemberId;
    }

    public Message postedByMemberId(Long postedByMemberId) {
        this.postedByMemberId = postedByMemberId;
        return this;
    }

    public void setPostedByMemberId(Long postedByMemberId) {
        this.postedByMemberId = postedByMemberId;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public Message messageBody(String messageBody) {
        this.messageBody = messageBody;
        return this;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public Instant getWhenCreated() {
        return whenCreated;
    }

    public Message whenCreated(Instant whenCreated) {
        this.whenCreated = whenCreated;
        return this;
    }

    public void setWhenCreated(Instant whenCreated) {
        this.whenCreated = whenCreated;
    }

    public Set<MessageItem> getMessageItems() {
        return messageItems;
    }

    public Message messageItems(Set<MessageItem> messageItems) {
        this.messageItems = messageItems;
        return this;
    }

    public Message addMessageItem(MessageItem messageItem) {
        this.messageItems.add(messageItem);
        messageItem.setMessage(this);
        return this;
    }

    public Message removeMessageItem(MessageItem messageItem) {
        this.messageItems.remove(messageItem);
        messageItem.setMessage(null);
        return this;
    }

    public void setMessageItems(Set<MessageItem> messageItems) {
        this.messageItems = messageItems;
    }

    public Project getProject() {
        return project;
    }

    public Message project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public RecipientItem getRecipientItem() {
        return recipientItem;
    }

    public Message recipientItem(RecipientItem recipientItem) {
        this.recipientItem = recipientItem;
        return this;
    }

    public void setRecipientItem(RecipientItem recipientItem) {
        this.recipientItem = recipientItem;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Message)) {
            return false;
        }
        return id != null && id.equals(((Message) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Message{" +
            "id=" + getId() +
            ", recipientId=" + getRecipientId() +
            ", postedByMemberId=" + getPostedByMemberId() +
            ", messageBody='" + getMessageBody() + "'" +
            ", whenCreated='" + getWhenCreated() + "'" +
            "}";
    }
}
