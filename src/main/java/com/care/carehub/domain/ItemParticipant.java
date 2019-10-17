package com.care.carehub.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.care.carehub.domain.enumeration.Permission;

/**
 * A ItemParticipant.
 */
@Entity
@Table(name = "item_participant")
public class ItemParticipant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "permission", nullable = false)
    private Permission permission;

    @Column(name = "when_created")
    private Instant whenCreated;

    @ManyToOne
    @JsonIgnoreProperties("itemParticipants")
    private ProjectParticipant projectParticipant;

    @ManyToOne
    @JsonIgnoreProperties("itemParticipants")
    private RecipientItem recipientItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Permission getPermission() {
        return permission;
    }

    public ItemParticipant permission(Permission permission) {
        this.permission = permission;
        return this;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public Instant getWhenCreated() {
        return whenCreated;
    }

    public ItemParticipant whenCreated(Instant whenCreated) {
        this.whenCreated = whenCreated;
        return this;
    }

    public void setWhenCreated(Instant whenCreated) {
        this.whenCreated = whenCreated;
    }

    public ProjectParticipant getProjectParticipant() {
        return projectParticipant;
    }

    public ItemParticipant projectParticipant(ProjectParticipant projectParticipant) {
        this.projectParticipant = projectParticipant;
        return this;
    }

    public void setProjectParticipant(ProjectParticipant projectParticipant) {
        this.projectParticipant = projectParticipant;
    }

    public RecipientItem getRecipientItem() {
        return recipientItem;
    }

    public ItemParticipant recipientItem(RecipientItem recipientItem) {
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
        if (!(o instanceof ItemParticipant)) {
            return false;
        }
        return id != null && id.equals(((ItemParticipant) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ItemParticipant{" +
            "id=" + getId() +
            ", permission='" + getPermission() + "'" +
            ", whenCreated='" + getWhenCreated() + "'" +
            "}";
    }
}
