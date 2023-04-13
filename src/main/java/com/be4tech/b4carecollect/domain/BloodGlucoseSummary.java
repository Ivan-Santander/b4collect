package com.be4tech.b4carecollect.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A BloodGlucoseSummary.
 */
@Entity
@Table(name = "blood_glucose_summary")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BloodGlucoseSummary implements Serializable {

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

    @Column(name = "interval_food")
    private Integer intervalFood;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "meal_type")
    private String mealType;

    @Column(name = "relation_temporal_sleep")
    private Integer relationTemporalSleep;

    @Column(name = "sample_source")
    private Integer sampleSource;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public BloodGlucoseSummary id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return this.usuarioId;
    }

    public BloodGlucoseSummary usuarioId(String usuarioId) {
        this.setUsuarioId(usuarioId);
        return this;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEmpresaId() {
        return this.empresaId;
    }

    public BloodGlucoseSummary empresaId(String empresaId) {
        this.setEmpresaId(empresaId);
        return this;
    }

    public void setEmpresaId(String empresaId) {
        this.empresaId = empresaId;
    }

    public Float getFieldAverage() {
        return this.fieldAverage;
    }

    public BloodGlucoseSummary fieldAverage(Float fieldAverage) {
        this.setFieldAverage(fieldAverage);
        return this;
    }

    public void setFieldAverage(Float fieldAverage) {
        this.fieldAverage = fieldAverage;
    }

    public Float getFieldMax() {
        return this.fieldMax;
    }

    public BloodGlucoseSummary fieldMax(Float fieldMax) {
        this.setFieldMax(fieldMax);
        return this;
    }

    public void setFieldMax(Float fieldMax) {
        this.fieldMax = fieldMax;
    }

    public Float getFieldMin() {
        return this.fieldMin;
    }

    public BloodGlucoseSummary fieldMin(Float fieldMin) {
        this.setFieldMin(fieldMin);
        return this;
    }

    public void setFieldMin(Float fieldMin) {
        this.fieldMin = fieldMin;
    }

    public Integer getIntervalFood() {
        return this.intervalFood;
    }

    public BloodGlucoseSummary intervalFood(Integer intervalFood) {
        this.setIntervalFood(intervalFood);
        return this;
    }

    public void setIntervalFood(Integer intervalFood) {
        this.intervalFood = intervalFood;
    }

    public String getMealType() {
        return this.mealType;
    }

    public BloodGlucoseSummary mealType(String mealType) {
        this.setMealType(mealType);
        return this;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public Integer getRelationTemporalSleep() {
        return this.relationTemporalSleep;
    }

    public BloodGlucoseSummary relationTemporalSleep(Integer relationTemporalSleep) {
        this.setRelationTemporalSleep(relationTemporalSleep);
        return this;
    }

    public void setRelationTemporalSleep(Integer relationTemporalSleep) {
        this.relationTemporalSleep = relationTemporalSleep;
    }

    public Integer getSampleSource() {
        return this.sampleSource;
    }

    public BloodGlucoseSummary sampleSource(Integer sampleSource) {
        this.setSampleSource(sampleSource);
        return this;
    }

    public void setSampleSource(Integer sampleSource) {
        this.sampleSource = sampleSource;
    }

    public Instant getStartTime() {
        return this.startTime;
    }

    public BloodGlucoseSummary startTime(Instant startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    public BloodGlucoseSummary endTime(Instant endTime) {
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
        if (!(o instanceof BloodGlucoseSummary)) {
            return false;
        }
        return id != null && id.equals(((BloodGlucoseSummary) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BloodGlucoseSummary{" +
            "id=" + getId() +
            ", usuarioId='" + getUsuarioId() + "'" +
            ", empresaId='" + getEmpresaId() + "'" +
            ", fieldAverage=" + getFieldAverage() +
            ", fieldMax=" + getFieldMax() +
            ", fieldMin=" + getFieldMin() +
            ", intervalFood=" + getIntervalFood() +
            ", mealType='" + getMealType() + "'" +
            ", relationTemporalSleep=" + getRelationTemporalSleep() +
            ", sampleSource=" + getSampleSource() +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            "}";
    }
}
