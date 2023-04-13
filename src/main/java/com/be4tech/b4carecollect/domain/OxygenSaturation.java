package com.be4tech.b4carecollect.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OxygenSaturation.
 */
@Entity
@Table(name = "oxygen_saturation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OxygenSaturation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "usuario_id")
    private String usuarioId;

    @Column(name = "empresa_id")
    private String empresaId;

    @Column(name = "field_oxigen_saturation")
    private Float fieldOxigenSaturation;

    @Column(name = "field_suplemental_oxigen_flow_rate")
    private Float fieldSuplementalOxigenFlowRate;

    @Column(name = "field_oxigen_therapy_administration_mode")
    private Integer fieldOxigenTherapyAdministrationMode;

    @Column(name = "field_oxigen_saturation_mode")
    private Integer fieldOxigenSaturationMode;

    @Column(name = "field_oxigen_saturation_measurement_method")
    private Integer fieldOxigenSaturationMeasurementMethod;

    @Column(name = "end_time")
    private Instant endTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public OxygenSaturation id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return this.usuarioId;
    }

    public OxygenSaturation usuarioId(String usuarioId) {
        this.setUsuarioId(usuarioId);
        return this;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEmpresaId() {
        return this.empresaId;
    }

    public OxygenSaturation empresaId(String empresaId) {
        this.setEmpresaId(empresaId);
        return this;
    }

    public void setEmpresaId(String empresaId) {
        this.empresaId = empresaId;
    }

    public Float getFieldOxigenSaturation() {
        return this.fieldOxigenSaturation;
    }

    public OxygenSaturation fieldOxigenSaturation(Float fieldOxigenSaturation) {
        this.setFieldOxigenSaturation(fieldOxigenSaturation);
        return this;
    }

    public void setFieldOxigenSaturation(Float fieldOxigenSaturation) {
        this.fieldOxigenSaturation = fieldOxigenSaturation;
    }

    public Float getFieldSuplementalOxigenFlowRate() {
        return this.fieldSuplementalOxigenFlowRate;
    }

    public OxygenSaturation fieldSuplementalOxigenFlowRate(Float fieldSuplementalOxigenFlowRate) {
        this.setFieldSuplementalOxigenFlowRate(fieldSuplementalOxigenFlowRate);
        return this;
    }

    public void setFieldSuplementalOxigenFlowRate(Float fieldSuplementalOxigenFlowRate) {
        this.fieldSuplementalOxigenFlowRate = fieldSuplementalOxigenFlowRate;
    }

    public Integer getFieldOxigenTherapyAdministrationMode() {
        return this.fieldOxigenTherapyAdministrationMode;
    }

    public OxygenSaturation fieldOxigenTherapyAdministrationMode(Integer fieldOxigenTherapyAdministrationMode) {
        this.setFieldOxigenTherapyAdministrationMode(fieldOxigenTherapyAdministrationMode);
        return this;
    }

    public void setFieldOxigenTherapyAdministrationMode(Integer fieldOxigenTherapyAdministrationMode) {
        this.fieldOxigenTherapyAdministrationMode = fieldOxigenTherapyAdministrationMode;
    }

    public Integer getFieldOxigenSaturationMode() {
        return this.fieldOxigenSaturationMode;
    }

    public OxygenSaturation fieldOxigenSaturationMode(Integer fieldOxigenSaturationMode) {
        this.setFieldOxigenSaturationMode(fieldOxigenSaturationMode);
        return this;
    }

    public void setFieldOxigenSaturationMode(Integer fieldOxigenSaturationMode) {
        this.fieldOxigenSaturationMode = fieldOxigenSaturationMode;
    }

    public Integer getFieldOxigenSaturationMeasurementMethod() {
        return this.fieldOxigenSaturationMeasurementMethod;
    }

    public OxygenSaturation fieldOxigenSaturationMeasurementMethod(Integer fieldOxigenSaturationMeasurementMethod) {
        this.setFieldOxigenSaturationMeasurementMethod(fieldOxigenSaturationMeasurementMethod);
        return this;
    }

    public void setFieldOxigenSaturationMeasurementMethod(Integer fieldOxigenSaturationMeasurementMethod) {
        this.fieldOxigenSaturationMeasurementMethod = fieldOxigenSaturationMeasurementMethod;
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    public OxygenSaturation endTime(Instant endTime) {
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
        if (!(o instanceof OxygenSaturation)) {
            return false;
        }
        return id != null && id.equals(((OxygenSaturation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OxygenSaturation{" +
            "id=" + getId() +
            ", usuarioId='" + getUsuarioId() + "'" +
            ", empresaId='" + getEmpresaId() + "'" +
            ", fieldOxigenSaturation=" + getFieldOxigenSaturation() +
            ", fieldSuplementalOxigenFlowRate=" + getFieldSuplementalOxigenFlowRate() +
            ", fieldOxigenTherapyAdministrationMode=" + getFieldOxigenTherapyAdministrationMode() +
            ", fieldOxigenSaturationMode=" + getFieldOxigenSaturationMode() +
            ", fieldOxigenSaturationMeasurementMethod=" + getFieldOxigenSaturationMeasurementMethod() +
            ", endTime='" + getEndTime() + "'" +
            "}";
    }
}
