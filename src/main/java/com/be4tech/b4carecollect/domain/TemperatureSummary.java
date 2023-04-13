package com.be4tech.b4carecollect.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TemperatureSummary.
 */
@Entity
@Table(name = "temperature_summary")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TemperatureSummary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "usuario_id")
    private String usuarioId;

    @Column(name = "empresa_id")
    private String empresaId;

    @Column(name = "field_average")
    private Float fieldAverage;

    @Column(name = "field_max")
    private Float fieldMax;

    @Column(name = "field_min")
    private Float fieldMin;

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

    public TemperatureSummary id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return this.usuarioId;
    }

    public TemperatureSummary usuarioId(String usuarioId) {
        this.setUsuarioId(usuarioId);
        return this;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEmpresaId() {
        return this.empresaId;
    }

    public TemperatureSummary empresaId(String empresaId) {
        this.setEmpresaId(empresaId);
        return this;
    }

    public void setEmpresaId(String empresaId) {
        this.empresaId = empresaId;
    }

    public Float getFieldAverage() {
        return this.fieldAverage;
    }

    public TemperatureSummary fieldAverage(Float fieldAverage) {
        this.setFieldAverage(fieldAverage);
        return this;
    }

    public void setFieldAverage(Float fieldAverage) {
        this.fieldAverage = fieldAverage;
    }

    public Float getFieldMax() {
        return this.fieldMax;
    }

    public TemperatureSummary fieldMax(Float fieldMax) {
        this.setFieldMax(fieldMax);
        return this;
    }

    public void setFieldMax(Float fieldMax) {
        this.fieldMax = fieldMax;
    }

    public Float getFieldMin() {
        return this.fieldMin;
    }

    public TemperatureSummary fieldMin(Float fieldMin) {
        this.setFieldMin(fieldMin);
        return this;
    }

    public void setFieldMin(Float fieldMin) {
        this.fieldMin = fieldMin;
    }

    public Integer getMeasurementLocation() {
        return this.measurementLocation;
    }

    public TemperatureSummary measurementLocation(Integer measurementLocation) {
        this.setMeasurementLocation(measurementLocation);
        return this;
    }

    public void setMeasurementLocation(Integer measurementLocation) {
        this.measurementLocation = measurementLocation;
    }

    public Instant getStartTime() {
        return this.startTime;
    }

    public TemperatureSummary startTime(Instant startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    public TemperatureSummary endTime(Instant endTime) {
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
        if (!(o instanceof TemperatureSummary)) {
            return false;
        }
        return id != null && id.equals(((TemperatureSummary) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TemperatureSummary{" +
            "id=" + getId() +
            ", usuarioId='" + getUsuarioId() + "'" +
            ", empresaId='" + getEmpresaId() + "'" +
            ", fieldAverage=" + getFieldAverage() +
            ", fieldMax=" + getFieldMax() +
            ", fieldMin=" + getFieldMin() +
            ", measurementLocation=" + getMeasurementLocation() +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            "}";
    }
}
