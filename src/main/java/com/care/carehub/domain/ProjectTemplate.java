package com.care.carehub.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ProjectTemplate.
 */
@Entity
@Table(name = "project_template")
public class ProjectTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "service_id", nullable = false)
    private Long serviceId;

    @NotNull
    @Column(name = "template_description", nullable = false)
    private String templateDescription;

    @NotNull
    @Column(name = "template_creation_class", nullable = false)
    private String templateCreationClass;

    @Column(name = "when_created")
    private String whenCreated;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public ProjectTemplate serviceId(Long serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getTemplateDescription() {
        return templateDescription;
    }

    public ProjectTemplate templateDescription(String templateDescription) {
        this.templateDescription = templateDescription;
        return this;
    }

    public void setTemplateDescription(String templateDescription) {
        this.templateDescription = templateDescription;
    }

    public String getTemplateCreationClass() {
        return templateCreationClass;
    }

    public ProjectTemplate templateCreationClass(String templateCreationClass) {
        this.templateCreationClass = templateCreationClass;
        return this;
    }

    public void setTemplateCreationClass(String templateCreationClass) {
        this.templateCreationClass = templateCreationClass;
    }

    public String getWhenCreated() {
        return whenCreated;
    }

    public ProjectTemplate whenCreated(String whenCreated) {
        this.whenCreated = whenCreated;
        return this;
    }

    public void setWhenCreated(String whenCreated) {
        this.whenCreated = whenCreated;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectTemplate)) {
            return false;
        }
        return id != null && id.equals(((ProjectTemplate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProjectTemplate{" +
            "id=" + getId() +
            ", serviceId=" + getServiceId() +
            ", templateDescription='" + getTemplateDescription() + "'" +
            ", templateCreationClass='" + getTemplateCreationClass() + "'" +
            ", whenCreated='" + getWhenCreated() + "'" +
            "}";
    }
}
