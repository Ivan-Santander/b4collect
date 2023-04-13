package com.be4tech.b4carecollect.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FuntionalIndexSummary.
 */
@Entity
@Table(name = "funtional_index_summary")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FuntionalIndexSummary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "usuario_id")
    private String usuarioId;

    @Column(name = "empresa_id")
    private String empresaId;

    @Column(name = "field_funtional_index_average")
    private Float fieldFuntionalIndexAverage;

    @Column(name = "field_funtional_index_max")
    private Float fieldFuntionalIndexMax;

    @Column(name = "field_funtional_index_min")
    private Float fieldFuntionalIndexMin;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public FuntionalIndexSummary id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return this.usuarioId;
    }

    public FuntionalIndexSummary usuarioId(String usuarioId) {
        this.setUsuarioId(usuarioId);
        return this;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEmpresaId() {
        return this.empresaId;
    }

    public FuntionalIndexSummary empresaId(String empresaId) {
        this.setEmpresaId(empresaId);
        return this;
    }

    public void setEmpresaId(String empresaId) {
        this.empresaId = empresaId;
    }

    public Float getFieldFuntionalIndexAverage() {
        return this.fieldFuntionalIndexAverage;
    }

    public FuntionalIndexSummary fieldFuntionalIndexAverage(Float fieldFuntionalIndexAverage) {
        this.setFieldFuntionalIndexAverage(fieldFuntionalIndexAverage);
        return this;
    }

    public void setFieldFuntionalIndexAverage(Float fieldFuntionalIndexAverage) {
        this.fieldFuntionalIndexAverage = fieldFuntionalIndexAverage;
    }

    public Float getFieldFuntionalIndexMax() {
        return this.fieldFuntionalIndexMax;
    }

    public FuntionalIndexSummary fieldFuntionalIndexMax(Float fieldFuntionalIndexMax) {
        this.setFieldFuntionalIndexMax(fieldFuntionalIndexMax);
        return this;
    }

    public void setFieldFuntionalIndexMax(Float fieldFuntionalIndexMax) {
        this.fieldFuntionalIndexMax = fieldFuntionalIndexMax;
    }

    public Float getFieldFuntionalIndexMin() {
        return this.fieldFuntionalIndexMin;
    }

    public FuntionalIndexSummary fieldFuntionalIndexMin(Float fieldFuntionalIndexMin) {
        this.setFieldFuntionalIndexMin(fieldFuntionalIndexMin);
        return this;
    }

    public void setFieldFuntionalIndexMin(Float fieldFuntionalIndexMin) {
        this.fieldFuntionalIndexMin = fieldFuntionalIndexMin;
    }

    public Instant getStartTime() {
        return this.startTime;
    }

    public FuntionalIndexSummary startTime(Instant startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    public FuntionalIndexSummary endTime(Instant endTime) {
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
        if (!(o instanceof FuntionalIndexSummary)) {
            return false;
        }
        return id != null && id.equals(((FuntionalIndexSummary) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FuntionalIndexSummary{" +
            "id=" + getId() +
            ", usuarioId='" + getUsuarioId() + "'" +
            ", empresaId='" + getEmpresaId() + "'" +
            ", fieldFuntionalIndexAverage=" + getFieldFuntionalIndexAverage() +
            ", fieldFuntionalIndexMax=" + getFieldFuntionalIndexMax() +
            ", fieldFuntionalIndexMin=" + getFieldFuntionalIndexMin() +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            "}";
    }
}
