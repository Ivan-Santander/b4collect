package com.be4tech.b4carecollect.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BloodGlucose.
 */
@Entity
@Table(name = "blood_glucose")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BloodGlucose implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "usuario_id")
    private String usuarioId;

    @Column(name = "empresa_id")
    private String empresaId;

    @Column(name = "field_blood_glucose_level")
    private Float fieldBloodGlucoseLevel;

    @Column(name = "field_temporal_relation_to_meal")
    private Integer fieldTemporalRelationToMeal;

    @Column(name = "field_meal_type")
    private Integer fieldMealType;

    @Column(name = "field_temporal_relation_to_sleep")
    private Integer fieldTemporalRelationToSleep;

    @Column(name = "field_blood_glucose_specimen_source")
    private Integer fieldBloodGlucoseSpecimenSource;

    @Column(name = "end_time")
    private Instant endTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public BloodGlucose id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return this.usuarioId;
    }

    public BloodGlucose usuarioId(String usuarioId) {
        this.setUsuarioId(usuarioId);
        return this;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEmpresaId() {
        return this.empresaId;
    }

    public BloodGlucose empresaId(String empresaId) {
        this.setEmpresaId(empresaId);
        return this;
    }

    public void setEmpresaId(String empresaId) {
        this.empresaId = empresaId;
    }

    public Float getFieldBloodGlucoseLevel() {
        return this.fieldBloodGlucoseLevel;
    }

    public BloodGlucose fieldBloodGlucoseLevel(Float fieldBloodGlucoseLevel) {
        this.setFieldBloodGlucoseLevel(fieldBloodGlucoseLevel);
        return this;
    }

    public void setFieldBloodGlucoseLevel(Float fieldBloodGlucoseLevel) {
        this.fieldBloodGlucoseLevel = fieldBloodGlucoseLevel;
    }

    public Integer getFieldTemporalRelationToMeal() {
        return this.fieldTemporalRelationToMeal;
    }

    public BloodGlucose fieldTemporalRelationToMeal(Integer fieldTemporalRelationToMeal) {
        this.setFieldTemporalRelationToMeal(fieldTemporalRelationToMeal);
        return this;
    }

    public void setFieldTemporalRelationToMeal(Integer fieldTemporalRelationToMeal) {
        this.fieldTemporalRelationToMeal = fieldTemporalRelationToMeal;
    }

    public Integer getFieldMealType() {
        return this.fieldMealType;
    }

    public BloodGlucose fieldMealType(Integer fieldMealType) {
        this.setFieldMealType(fieldMealType);
        return this;
    }

    public void setFieldMealType(Integer fieldMealType) {
        this.fieldMealType = fieldMealType;
    }

    public Integer getFieldTemporalRelationToSleep() {
        return this.fieldTemporalRelationToSleep;
    }

    public BloodGlucose fieldTemporalRelationToSleep(Integer fieldTemporalRelationToSleep) {
        this.setFieldTemporalRelationToSleep(fieldTemporalRelationToSleep);
        return this;
    }

    public void setFieldTemporalRelationToSleep(Integer fieldTemporalRelationToSleep) {
        this.fieldTemporalRelationToSleep = fieldTemporalRelationToSleep;
    }

    public Integer getFieldBloodGlucoseSpecimenSource() {
        return this.fieldBloodGlucoseSpecimenSource;
    }

    public BloodGlucose fieldBloodGlucoseSpecimenSource(Integer fieldBloodGlucoseSpecimenSource) {
        this.setFieldBloodGlucoseSpecimenSource(fieldBloodGlucoseSpecimenSource);
        return this;
    }

    public void setFieldBloodGlucoseSpecimenSource(Integer fieldBloodGlucoseSpecimenSource) {
        this.fieldBloodGlucoseSpecimenSource = fieldBloodGlucoseSpecimenSource;
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    public BloodGlucose endTime(Instant endTime) {
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
        if (!(o instanceof BloodGlucose)) {
            return false;
        }
        return id != null && id.equals(((BloodGlucose) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BloodGlucose{" +
            "id=" + getId() +
            ", usuarioId='" + getUsuarioId() + "'" +
            ", empresaId='" + getEmpresaId() + "'" +
            ", fieldBloodGlucoseLevel=" + getFieldBloodGlucoseLevel() +
            ", fieldTemporalRelationToMeal=" + getFieldTemporalRelationToMeal() +
            ", fieldMealType=" + getFieldMealType() +
            ", fieldTemporalRelationToSleep=" + getFieldTemporalRelationToSleep() +
            ", fieldBloodGlucoseSpecimenSource=" + getFieldBloodGlucoseSpecimenSource() +
            ", endTime='" + getEndTime() + "'" +
            "}";
    }
}
