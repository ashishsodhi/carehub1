package com.care.carehub.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.care.carehub.domain.enumeration.TaskStatus;

/**
 * A Task.
 */
@Entity
@Table(name = "task")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "category")
    private String category;

    @Column(name = "assigned_to_member")
    private Long assignedToMember;

    @Column(name = "due_date")
    private Instant dueDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskStatus status;

    @Column(name = "status_tlm")
    private Instant statusTLM;

    @Column(name = "when_created")
    private Instant whenCreated;

    @OneToOne(mappedBy = "task")
    @JsonIgnore
    private RecipientItem recipientItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Task name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Task description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public Task category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getAssignedToMember() {
        return assignedToMember;
    }

    public Task assignedToMember(Long assignedToMember) {
        this.assignedToMember = assignedToMember;
        return this;
    }

    public void setAssignedToMember(Long assignedToMember) {
        this.assignedToMember = assignedToMember;
    }

    public Instant getDueDate() {
        return dueDate;
    }

    public Task dueDate(Instant dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public Task status(TaskStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Instant getStatusTLM() {
        return statusTLM;
    }

    public Task statusTLM(Instant statusTLM) {
        this.statusTLM = statusTLM;
        return this;
    }

    public void setStatusTLM(Instant statusTLM) {
        this.statusTLM = statusTLM;
    }

    public Instant getWhenCreated() {
        return whenCreated;
    }

    public Task whenCreated(Instant whenCreated) {
        this.whenCreated = whenCreated;
        return this;
    }

    public void setWhenCreated(Instant whenCreated) {
        this.whenCreated = whenCreated;
    }

    public RecipientItem getRecipientItem() {
        return recipientItem;
    }

    public Task recipientItem(RecipientItem recipientItem) {
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
        if (!(o instanceof Task)) {
            return false;
        }
        return id != null && id.equals(((Task) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Task{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", category='" + getCategory() + "'" +
            ", assignedToMember=" + getAssignedToMember() +
            ", dueDate='" + getDueDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", statusTLM='" + getStatusTLM() + "'" +
            ", whenCreated='" + getWhenCreated() + "'" +
            "}";
    }
}
