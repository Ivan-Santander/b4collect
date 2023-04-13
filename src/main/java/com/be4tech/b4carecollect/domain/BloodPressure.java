package com.be4tech.b4carecollect.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BloodPressure.
 */
@Entity
@Table(name = "blood_pressure")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BloodPressure implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "usuario_id")
    private String usuarioId;

    @Column(name = "empresa_id")
    private String empresaId;

    @Column(name = "field_blood_pressure_systolic")
    private Float fieldBloodPressureSystolic;

    @Column(name = "field_blood_pressure_diastolic")
    private Float fieldBloodPressureDiastolic;

    @Column(name = "field_body_position")
    private String fieldBodyPosition;

    @Column(name = "field_blood_pressure_measure_location")
    private Integer fieldBloodPressureMeasureLocation;

    @Column(name = "end_time")
    private Instant endTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public BloodPressure id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return this.usuarioId;
    }

    public BloodPressure usuarioId(String usuarioId) {
        this.setUsuarioId(usuarioId);
        return this;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEmpresaId() {
        return this.empresaId;
    }

    public BloodPressure empresaId(String empresaId) {
        this.setEmpresaId(empresaId);
        return this;
    }

    public void setEmpresaId(String empresaId) {
        this.empresaId = empresaId;
    }

    public Float getFieldBloodPressureSystolic() {
        return this.fieldBloodPressureSystolic;
    }

    public BloodPressure fieldBloodPressureSystolic(Float fieldBloodPressureSystolic) {
        this.setFieldBloodPressureSystolic(fieldBloodPressureSystolic);
        return this;
    }

    public void setFieldBloodPressureSystolic(Float fieldBloodPressureSystolic) {
        this.fieldBloodPressureSystolic = fieldBloodPressureSystolic;
    }

    public Float getFieldBloodPressureDiastolic() {
        return this.fieldBloodPressureDiastolic;
    }

    public BloodPressure fieldBloodPressureDiastolic(Float fieldBloodPressureDiastolic) {
        this.setFieldBloodPressureDiastolic(fieldBloodPressureDiastolic);
        return this;
    }

    public void setFieldBloodPressureDiastolic(Float fieldBloodPressureDiastolic) {
        this.fieldBloodPressureDiastolic = fieldBloodPressureDiastolic;
    }

    public String getFieldBodyPosition() {
        return this.fieldBodyPosition;
    }

    public BloodPressure fieldBodyPosition(String fieldBodyPosition) {
        this.setFieldBodyPosition(fieldBodyPosition);
        return this;
    }

    public void setFieldBodyPosition(String fieldBodyPosition) {
        this.fieldBodyPosition = fieldBodyPosition;
    }

    public Integer getFieldBloodPressureMeasureLocation() {
        return this.fieldBloodPressureMeasureLocation;
    }

    public BloodPressure fieldBloodPressureMeasureLocation(Integer fieldBloodPressureMeasureLocation) {
        this.setFieldBloodPressureMeasureLocation(fieldBloodPressureMeasureLocation);
        return this;
    }

    public void setFieldBloodPressureMeasureLocation(Integer fieldBloodPressureMeasureLocation) {
        this.fieldBloodPressureMeasureLocation = fieldBloodPressureMeasureLocation;
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    public BloodPressure endTime(Instant endTime) {
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
        if (!(o instanceof BloodPressure)) {
            return false;
        }
        return id != null && id.equals(((BloodPressure) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BloodPressure{" +
            "id=" + getId() +
            ", usuarioId='" + getUsuarioId() + "'" +
            ", empresaId='" + getEmpresaId() + "'" +
            ", fieldBloodPressureSystolic=" + getFieldBloodPressureSystolic() +
            ", fieldBloodPressureDiastolic=" + getFieldBloodPressureDiastolic() +
            ", fieldBodyPosition='" + getFieldBodyPosition() + "'" +
            ", fieldBloodPressureMeasureLocation=" + getFieldBloodPressureMeasureLocation() +
            ", endTime='" + getEndTime() + "'" +
            "}";
    }
}
