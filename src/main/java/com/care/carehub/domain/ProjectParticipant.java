package com.care.carehub.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.care.carehub.domain.enumeration.Permission;

import com.care.carehub.domain.enumeration.ParticipantStatus;

/**
 * A ProjectParticipant.
 */
@Entity
@Table(name = "project_participant")
public class ProjectParticipant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @NotNull
    @Column(name = "inviter_id", nullable = false)
    private Long inviterId;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "email_address", nullable = false)
    private String emailAddress;

    @Column(name = "relationship_to_inviter")
    private String relationshipToInviter;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "permission", nullable = false)
    private Permission permission;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ParticipantStatus status;

    @Column(name = "status_tlm")
    private Instant statusTLM;

    @Column(name = "when_created")
    private Instant whenCreated;

    @OneToMany(mappedBy = "projectParticipant")
    private Set<ItemParticipant> itemParticipants = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("projectParticipants")
    private Project project;

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

    public ProjectParticipant memberId(Long memberId) {
        this.memberId = memberId;
        return this;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getInviterId() {
        return inviterId;
    }

    public ProjectParticipant inviterId(Long inviterId) {
        this.inviterId = inviterId;
        return this;
    }

    public void setInviterId(Long inviterId) {
        this.inviterId = inviterId;
    }

    public String getFirstName() {
        return firstName;
    }

    public ProjectParticipant firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public ProjectParticipant emailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getRelationshipToInviter() {
        return relationshipToInviter;
    }

    public ProjectParticipant relationshipToInviter(String relationshipToInviter) {
        this.relationshipToInviter = relationshipToInviter;
        return this;
    }

    public void setRelationshipToInviter(String relationshipToInviter) {
        this.relationshipToInviter = relationshipToInviter;
    }

    public Permission getPermission() {
        return permission;
    }

    public ProjectParticipant permission(Permission permission) {
        this.permission = permission;
        return this;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public ParticipantStatus getStatus() {
        return status;
    }

    public ProjectParticipant status(ParticipantStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ParticipantStatus status) {
        this.status = status;
    }

    public Instant getStatusTLM() {
        return statusTLM;
    }

    public ProjectParticipant statusTLM(Instant statusTLM) {
        this.statusTLM = statusTLM;
        return this;
    }

    public void setStatusTLM(Instant statusTLM) {
        this.statusTLM = statusTLM;
    }

    public Instant getWhenCreated() {
        return whenCreated;
    }

    public ProjectParticipant whenCreated(Instant whenCreated) {
        this.whenCreated = whenCreated;
        return this;
    }

    public void setWhenCreated(Instant whenCreated) {
        this.whenCreated = whenCreated;
    }

    public Set<ItemParticipant> getItemParticipants() {
        return itemParticipants;
    }

    public ProjectParticipant itemParticipants(Set<ItemParticipant> itemParticipants) {
        this.itemParticipants = itemParticipants;
        return this;
    }

    public ProjectParticipant addItemParticipant(ItemParticipant itemParticipant) {
        this.itemParticipants.add(itemParticipant);
        itemParticipant.setProjectParticipant(this);
        return this;
    }

    public ProjectParticipant removeItemParticipant(ItemParticipant itemParticipant) {
        this.itemParticipants.remove(itemParticipant);
        itemParticipant.setProjectParticipant(null);
        return this;
    }

    public void setItemParticipants(Set<ItemParticipant> itemParticipants) {
        this.itemParticipants = itemParticipants;
    }

    public Project getProject() {
        return project;
    }

    public ProjectParticipant project(Project project) {
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
        if (!(o instanceof ProjectParticipant)) {
            return false;
        }
        return id != null && id.equals(((ProjectParticipant) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProjectParticipant{" +
            "id=" + getId() +
            ", memberId=" + getMemberId() +
            ", inviterId=" + getInviterId() +
            ", firstName='" + getFirstName() + "'" +
            ", emailAddress='" + getEmailAddress() + "'" +
            ", relationshipToInviter='" + getRelationshipToInviter() + "'" +
            ", permission='" + getPermission() + "'" +
            ", status='" + getStatus() + "'" +
            ", statusTLM='" + getStatusTLM() + "'" +
            ", whenCreated='" + getWhenCreated() + "'" +
            "}";
    }
}
