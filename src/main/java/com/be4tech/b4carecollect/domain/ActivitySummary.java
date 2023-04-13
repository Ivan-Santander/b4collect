package com.be4tech.b4carecollect.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ActivitySummary.
 */
@Entity
@Table(name = "activity_summary")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ActivitySummary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "usuario_id")
    private String usuarioId;

    @Column(name = "empresa_id")
    private String empresaId;

    @Column(name = "field_activity")
    private Integer fieldActivity;

    @Column(name = "field_duration")
    private Integer fieldDuration;

    @Column(name = "field_num_segments")
    private Integer fieldNumSegments;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public ActivitySummary id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return this.usuarioId;
    }

    public ActivitySummary usuarioId(String usuarioId) {
        this.setUsuarioId(usuarioId);
        return this;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEmpresaId() {
        return this.empresaId;
    }

    public ActivitySummary empresaId(String empresaId) {
        this.setEmpresaId(empresaId);
        return this;
    }

    public void setEmpresaId(String empresaId) {
        this.empresaId = empresaId;
    }

    public Integer getFieldActivity() {
        return this.fieldActivity;
    }

    public ActivitySummary fieldActivity(Integer fieldActivity) {
        this.setFieldActivity(fieldActivity);
        return this;
    }

    public void setFieldActivity(Integer fieldActivity) {
        this.fieldActivity = fieldActivity;
    }

    public Integer getFieldDuration() {
        return this.fieldDuration;
    }

    public ActivitySummary fieldDuration(Integer fieldDuration) {
        this.setFieldDuration(fieldDuration);
        return this;
    }

    public void setFieldDuration(Integer fieldDuration) {
        this.fieldDuration = fieldDuration;
    }

    public Integer getFieldNumSegments() {
        return this.fieldNumSegments;
    }

    public ActivitySummary fieldNumSegments(Integer fieldNumSegments) {
        this.setFieldNumSegments(fieldNumSegments);
        return this;
    }

    public void setFieldNumSegments(Integer fieldNumSegments) {
        this.fieldNumSegments = fieldNumSegments;
    }

    public Instant getStartTime() {
        return this.startTime;
    }

    public ActivitySummary startTime(Instant startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    public ActivitySummary endTime(Instant endTime) {
        this.setEndTime(endTime);
        return this;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActivitySummary)) {
            return false;
        }
        return id != null && id.equals(((ActivitySummary) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActivitySummary{" +
            "id=" + getId() +
            ", usuarioId='" + getUsuarioId() + "'" +
            ", empresaId='" + getEmpresaId() + "'" +
            ", fieldActivity=" + getFieldActivity() +
            ", fieldDuration=" + getFieldDuration() +
            ", fieldNumSegments=" + getFieldNumSegments() +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            "}";
    }
}
