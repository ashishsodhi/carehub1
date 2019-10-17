package com.care.carehub.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.care.carehub.domain.enumeration.Status;

/**
 * A Responsibilities.
 */
@Entity
@Table(name = "responsibilities")
public class Responsibilities implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "when_created")
    private Instant whenCreated;

    @ManyToOne
    @JsonIgnoreProperties("responsibilities")
    private Recipient recipient;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Responsibilities description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public Responsibilities status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Instant getWhenCreated() {
        return whenCreated;
    }

    public Responsibilities whenCreated(Instant whenCreated) {
        this.whenCreated = whenCreated;
        return this;
    }

    public void setWhenCreated(Instant whenCreated) {
        this.whenCreated = whenCreated;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public Responsibilities recipient(Recipient recipient) {
        this.recipient = recipient;
        return this;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Responsibilities)) {
            return false;
        }
        return id != null && id.equals(((Responsibilities) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Responsibilities{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            ", whenCreated='" + getWhenCreated() + "'" +
            "}";
    }
}
