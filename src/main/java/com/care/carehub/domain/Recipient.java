package com.care.carehub.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.care.carehub.domain.enumeration.Status;

/**
 * A Recipient.
 */
@Entity
@Table(name = "recipient")
public class Recipient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "relationship_to_you")
    private String relationshipToYou;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "status_tlm")
    private Instant statusTLM;

    @Column(name = "when_created")
    private Instant whenCreated;

    @OneToMany(mappedBy = "recipient")
    private Set<RecipientItem> recipientItems = new HashSet<>();

    @OneToMany(mappedBy = "recipient")
    private Set<Concerns> concerns = new HashSet<>();

    @OneToMany(mappedBy = "recipient")
    private Set<Responsibilities> responsibilities = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("recipients")
    private Project project;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRelationshipToYou() {
        return relationshipToYou;
    }

    public Recipient relationshipToYou(String relationshipToYou) {
        this.relationshipToYou = relationshipToYou;
        return this;
    }

    public void setRelationshipToYou(String relationshipToYou) {
        this.relationshipToYou = relationshipToYou;
    }

    public Status getStatus() {
        return status;
    }

    public Recipient status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Instant getStatusTLM() {
        return statusTLM;
    }

    public Recipient statusTLM(Instant statusTLM) {
        this.statusTLM = statusTLM;
        return this;
    }

    public void setStatusTLM(Instant statusTLM) {
        this.statusTLM = statusTLM;
    }

    public Instant getWhenCreated() {
        return whenCreated;
    }

    public Recipient whenCreated(Instant whenCreated) {
        this.whenCreated = whenCreated;
        return this;
    }

    public void setWhenCreated(Instant whenCreated) {
        this.whenCreated = whenCreated;
    }

    public Set<RecipientItem> getRecipientItems() {
        return recipientItems;
    }

    public Recipient recipientItems(Set<RecipientItem> recipientItems) {
        this.recipientItems = recipientItems;
        return this;
    }

    public Recipient addRecipientItem(RecipientItem recipientItem) {
        this.recipientItems.add(recipientItem);
        recipientItem.setRecipient(this);
        return this;
    }

    public Recipient removeRecipientItem(RecipientItem recipientItem) {
        this.recipientItems.remove(recipientItem);
        recipientItem.setRecipient(null);
        return this;
    }

    public void setRecipientItems(Set<RecipientItem> recipientItems) {
        this.recipientItems = recipientItems;
    }

    public Set<Concerns> getConcerns() {
        return concerns;
    }

    public Recipient concerns(Set<Concerns> concerns) {
        this.concerns = concerns;
        return this;
    }

    public Recipient addConcerns(Concerns concerns) {
        this.concerns.add(concerns);
        concerns.setRecipient(this);
        return this;
    }

    public Recipient removeConcerns(Concerns concerns) {
        this.concerns.remove(concerns);
        concerns.setRecipient(null);
        return this;
    }

    public void setConcerns(Set<Concerns> concerns) {
        this.concerns = concerns;
    }

    public Set<Responsibilities> getResponsibilities() {
        return responsibilities;
    }

    public Recipient responsibilities(Set<Responsibilities> responsibilities) {
        this.responsibilities = responsibilities;
        return this;
    }

    public Recipient addResponsibilities(Responsibilities responsibilities) {
        this.responsibilities.add(responsibilities);
        responsibilities.setRecipient(this);
        return this;
    }

    public Recipient removeResponsibilities(Responsibilities responsibilities) {
        this.responsibilities.remove(responsibilities);
        responsibilities.setRecipient(null);
        return this;
    }

    public void setResponsibilities(Set<Responsibilities> responsibilities) {
        this.responsibilities = responsibilities;
    }

    public Project getProject() {
        return project;
    }

    public Recipient project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Recipient)) {
            return false;
        }
        return id != null && id.equals(((Recipient) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Recipient{" +
            "id=" + getId() +
            ", relationshipToYou='" + getRelationshipToYou() + "'" +
            ", status='" + getStatus() + "'" +
            ", statusTLM='" + getStatusTLM() + "'" +
            ", whenCreated='" + getWhenCreated() + "'" +
            "}";
    }
}
