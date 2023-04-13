package com.be4tech.b4carecollect.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BloodPressureSummary.
 */
@Entity
@Table(name = "blood_pressure_summary")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BloodPressureSummary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "usuario_id")
    private String usuarioId;

    @Column(name = "empresa_id")
    private String empresaId;

    @Column(name = "field_sistolic_average")
    private Float fieldSistolicAverage;

    @Column(name = "field_sistolic_max")
    private Float fieldSistolicMax;

    @Column(name = "field_sistolic_min")
    private Float fieldSistolicMin;

    @Column(name = "field_diasatolic_average")
    private Float fieldDiasatolicAverage;

    @Column(name = "field_diastolic_max")
    private Float fieldDiastolicMax;

    @Column(name = "field_diastolic_min")
    private Float fieldDiastolicMin;

    @Column(name = "body_position")
    private Integer bodyPosition;

    @Column(name = "measurement_location")
    private Integer measurementLocation;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public BloodPressureSummary id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return this.usuarioId;
    }

    public BloodPressureSummary usuarioId(String usuarioId) {
        this.setUsuarioId(usuarioId);
        return this;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEmpresaId() {
        return this.empresaId;
    }

    public BloodPressureSummary empresaId(String empresaId) {
        this.setEmpresaId(empresaId);
        return this;
    }

    public void setEmpresaId(String empresaId) {
        this.empresaId = empresaId;
    }

    public Float getFieldSistolicAverage() {
        return this.fieldSistolicAverage;
    }

    public BloodPressureSummary fieldSistolicAverage(Float fieldSistolicAverage) {
        this.setFieldSistolicAverage(fieldSistolicAverage);
        return this;
    }

    public void setFieldSistolicAverage(Float fieldSistolicAverage) {
        this.fieldSistolicAverage = fieldSistolicAverage;
    }

    public Float getFieldSistolicMax() {
        return this.fieldSistolicMax;
    }

    public BloodPressureSummary fieldSistolicMax(Float fieldSistolicMax) {
        this.setFieldSistolicMax(fieldSistolicMax);
        return this;
    }

    public void setFieldSistolicMax(Float fieldSistolicMax) {
        this.fieldSistolicMax = fieldSistolicMax;
    }

    public Float getFieldSistolicMin() {
        return this.fieldSistolicMin;
    }

    public BloodPressureSummary fieldSistolicMin(Float fieldSistolicMin) {
        this.setFieldSistolicMin(fieldSistolicMin);
        return this;
    }

    public void setFieldSistolicMin(Float fieldSistolicMin) {
        this.fieldSistolicMin = fieldSistolicMin;
    }

    public Float getFieldDiasatolicAverage() {
        return this.fieldDiasatolicAverage;
    }

    public BloodPressureSummary fieldDiasatolicAverage(Float fieldDiasatolicAverage) {
        this.setFieldDiasatolicAverage(fieldDiasatolicAverage);
        return this;
    }

    public void setFieldDiasatolicAverage(Float fieldDiasatolicAverage) {
        this.fieldDiasatolicAverage = fieldDiasatolicAverage;
    }

    public Float getFieldDiastolicMax() {
        return this.fieldDiastolicMax;
    }

    public BloodPressureSummary fieldDiastolicMax(Float fieldDiastolicMax) {
        this.setFieldDiastolicMax(fieldDiastolicMax);
        return this;
    }

    public void setFieldDiastolicMax(Float fieldDiastolicMax) {
        this.fieldDiastolicMax = fieldDiastolicMax;
    }

    public Float getFieldDiastolicMin() {
        return this.fieldDiastolicMin;
    }

    public BloodPressureSummary fieldDiastolicMin(Float fieldDiastolicMin) {
        this.setFieldDiastolicMin(fieldDiastolicMin);
        return this;
    }

    public void setFieldDiastolicMin(Float fieldDiastolicMin) {
        this.fieldDiastolicMin = fieldDiastolicMin;
    }

    public Integer getBodyPosition() {
        return this.bodyPosition;
    }

    public BloodPressureSummary bodyPosition(Integer bodyPosition) {
        this.setBodyPosition(bodyPosition);
        return this;
    }

    public void setBodyPosition(Integer bodyPosition) {
        this.bodyPosition = bodyPosition;
    }

    public Integer getMeasurementLocation() {
        return this.measurementLocation;
    }

    public BloodPressureSummary measurementLocation(Integer measurementLocation) {
        this.setMeasurementLocation(measurementLocation);
        return this;
    }

    public void setMeasurementLocation(Integer measurementLocation) {
        this.measurementLocation = measurementLocation;
    }

    public Instant getStartTime() {
        return this.startTime;
    }

    public BloodPressureSummary startTime(Instant startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    public BloodPressureSummary endTime(Instant endTime) {
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
        if (!(o instanceof BloodPressureSummary)) {
            return false;
        }
        return id != null && id.equals(((BloodPressureSummary) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BloodPressureSummary{" +
            "id=" + getId() +
            ", usuarioId='" + getUsuarioId() + "'" +
            ", empresaId='" + getEmpresaId() + "'" +
            ", fieldSistolicAverage=" + getFieldSistolicAverage() +
            ", fieldSistolicMax=" + getFieldSistolicMax() +
            ", fieldSistolicMin=" + getFieldSistolicMin() +
            ", fieldDiasatolicAverage=" + getFieldDiasatolicAverage() +
            ", fieldDiastolicMax=" + getFieldDiastolicMax() +
            ", fieldDiastolicMin=" + getFieldDiastolicMin() +
            ", bodyPosition=" + getBodyPosition() +
            ", measurementLocation=" + getMeasurementLocation() +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            "}";
    }
}
