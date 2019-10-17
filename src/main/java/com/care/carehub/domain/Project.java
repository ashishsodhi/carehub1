package com.care.carehub.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.care.carehub.domain.enumeration.Status;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @NotNull
    @Column(name = "created_by_member_id", nullable = false)
    private Long createdByMemberId;

    @NotNull
    @Column(name = "service_id", nullable = false)
    private Long serviceId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "status_tlm")
    private Instant statusTLM;

    @Column(name = "when_created")
    private Instant whenCreated;

    @OneToMany(mappedBy = "project")
    private Set<Recipient> recipients = new HashSet<>();

    @OneToMany(mappedBy = "project")
    private Set<ProjectParticipant> projectParticipants = new HashSet<>();

    @OneToMany(mappedBy = "project")
    private Set<Message> messages = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Project memberId(Long memberId) {
        this.memberId = memberId;
        return this;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getCreatedByMemberId() {
        return createdByMemberId;
    }

    public Project createdByMemberId(Long createdByMemberId) {
        this.createdByMemberId = createdByMemberId;
        return this;
    }

    public void setCreatedByMemberId(Long createdByMemberId) {
        this.createdByMemberId = createdByMemberId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public Project serviceId(Long serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Status getStatus() {
        return status;
    }

    public Project status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Instant getStatusTLM() {
        return statusTLM;
    }

    public Project statusTLM(Instant statusTLM) {
        this.statusTLM = statusTLM;
        return this;
    }

    public void setStatusTLM(Instant statusTLM) {
        this.statusTLM = statusTLM;
    }

    public Instant getWhenCreated() {
        return whenCreated;
    }

    public Project whenCreated(Instant whenCreated) {
        this.whenCreated = whenCreated;
        return this;
    }

    public void setWhenCreated(Instant whenCreated) {
        this.whenCreated = whenCreated;
    }

    public Set<Recipient> getRecipients() {
        return recipients;
    }

    public Project recipients(Set<Recipient> recipients) {
        this.recipients = recipients;
        return this;
    }

    public Project addRecipient(Recipient recipient) {
        this.recipients.add(recipient);
        recipient.setProject(this);
        return this;
    }

    public Project removeRecipient(Recipient recipient) {
        this.recipients.remove(recipient);
        recipient.setProject(null);
        return this;
    }

    public void setRecipients(Set<Recipient> recipients) {
        this.recipients = recipients;
    }

    public Set<ProjectParticipant> getProjectParticipants() {
        return projectParticipants;
    }

    public Project projectParticipants(Set<ProjectParticipant> projectParticipants) {
        this.projectParticipants = projectParticipants;
        return this;
    }

    public Project addProjectParticipant(ProjectParticipant projectParticipant) {
        this.projectParticipants.add(projectParticipant);
        projectParticipant.setProject(this);
        return this;
    }

    public Project removeProjectParticipant(ProjectParticipant projectParticipant) {
        this.projectParticipants.remove(projectParticipant);
        projectParticipant.setProject(null);
        return this;
    }

    public void setProjectParticipants(Set<ProjectParticipant> projectParticipants) {
        this.projectParticipants = projectParticipants;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public Project messages(Set<Message> messages) {
        this.messages = messages;
        return this;
    }

    public Project addMessage(Message message) {
        this.messages.add(message);
        message.setProject(this);
        return this;
    }

    public Project removeMessage(Message message) {
        this.messages.remove(message);
        message.setProject(null);
        return this;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        return id != null && id.equals(((Project) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", memberId=" + getMemberId() +
            ", createdByMemberId=" + getCreatedByMemberId() +
            ", serviceId=" + getServiceId() +
            ", status='" + getStatus() + "'" +
            ", statusTLM='" + getStatusTLM() + "'" +
            ", whenCreated='" + getWhenCreated() + "'" +
            "}";
    }
}
