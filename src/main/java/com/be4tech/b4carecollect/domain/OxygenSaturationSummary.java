package com.be4tech.b4carecollect.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OxygenSaturationSummary.
 */
@Entity
@Table(name = "oxygen_saturation_summary")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OxygenSaturationSummary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "usuario_id")
    private String usuarioId;

    @Column(name = "empresa_id")
    private String empresaId;

    @Column(name = "field_oxigen_saturation_average")
    private Float fieldOxigenSaturationAverage;

    @Column(name = "field_oxigen_saturation_max")
    private Float fieldOxigenSaturationMax;

    @Column(name = "field_oxigen_saturation_min")
    private Float fieldOxigenSaturationMin;

    @Column(name = "field_suplemental_oxigen_flow_rate_average")
    private Float fieldSuplementalOxigenFlowRateAverage;

    @Column(name = "field_suplemental_oxigen_flow_rate_max")
    private Float fieldSuplementalOxigenFlowRateMax;

    @Column(name = "field_suplemental_oxigen_flow_rate_min")
    private Float fieldSuplementalOxigenFlowRateMin;

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

    public OxygenSaturationSummary id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return this.usuarioId;
    }

    public OxygenSaturationSummary usuarioId(String usuarioId) {
        this.setUsuarioId(usuarioId);
        return this;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEmpresaId() {
        return this.empresaId;
    }

    public OxygenSaturationSummary empresaId(String empresaId) {
        this.setEmpresaId(empresaId);
        return this;
    }

    public void setEmpresaId(String empresaId) {
        this.empresaId = empresaId;
    }

    public Float getFieldOxigenSaturationAverage() {
        return this.fieldOxigenSaturationAverage;
    }

    public OxygenSaturationSummary fieldOxigenSaturationAverage(Float fieldOxigenSaturationAverage) {
        this.setFieldOxigenSaturationAverage(fieldOxigenSaturationAverage);
        return this;
    }

    public void setFieldOxigenSaturationAverage(Float fieldOxigenSaturationAverage) {
        this.fieldOxigenSaturationAverage = fieldOxigenSaturationAverage;
    }

    public Float getFieldOxigenSaturationMax() {
        return this.fieldOxigenSaturationMax;
    }

    public OxygenSaturationSummary fieldOxigenSaturationMax(Float fieldOxigenSaturationMax) {
        this.setFieldOxigenSaturationMax(fieldOxigenSaturationMax);
        return this;
    }

    public void setFieldOxigenSaturationMax(Float fieldOxigenSaturationMax) {
        this.fieldOxigenSaturationMax = fieldOxigenSaturationMax;
    }

    public Float getFieldOxigenSaturationMin() {
        return this.fieldOxigenSaturationMin;
    }

    public OxygenSaturationSummary fieldOxigenSaturationMin(Float fieldOxigenSaturationMin) {
        this.setFieldOxigenSaturationMin(fieldOxigenSaturationMin);
        return this;
    }

    public void setFieldOxigenSaturationMin(Float fieldOxigenSaturationMin) {
        this.fieldOxigenSaturationMin = fieldOxigenSaturationMin;
    }

    public Float getFieldSuplementalOxigenFlowRateAverage() {
        return this.fieldSuplementalOxigenFlowRateAverage;
    }

    public OxygenSaturationSummary fieldSuplementalOxigenFlowRateAverage(Float fieldSuplementalOxigenFlowRateAverage) {
        this.setFieldSuplementalOxigenFlowRateAverage(fieldSuplementalOxigenFlowRateAverage);
        return this;
    }

    public void setFieldSuplementalOxigenFlowRateAverage(Float fieldSuplementalOxigenFlowRateAverage) {
        this.fieldSuplementalOxigenFlowRateAverage = fieldSuplementalOxigenFlowRateAverage;
    }

    public Float getFieldSuplementalOxigenFlowRateMax() {
        return this.fieldSuplementalOxigenFlowRateMax;
    }

    public OxygenSaturationSummary fieldSuplementalOxigenFlowRateMax(Float fieldSuplementalOxigenFlowRateMax) {
        this.setFieldSuplementalOxigenFlowRateMax(fieldSuplementalOxigenFlowRateMax);
        return this;
    }

    public void setFieldSuplementalOxigenFlowRateMax(Float fieldSuplementalOxigenFlowRateMax) {
        this.fieldSuplementalOxigenFlowRateMax = fieldSuplementalOxigenFlowRateMax;
    }

    public Float getFieldSuplementalOxigenFlowRateMin() {
        return this.fieldSuplementalOxigenFlowRateMin;
    }

    public OxygenSaturationSummary fieldSuplementalOxigenFlowRateMin(Float fieldSuplementalOxigenFlowRateMin) {
        this.setFieldSuplementalOxigenFlowRateMin(fieldSuplementalOxigenFlowRateMin);
        return this;
    }

    public void setFieldSuplementalOxigenFlowRateMin(Float fieldSuplementalOxigenFlowRateMin) {
        this.fieldSuplementalOxigenFlowRateMin = fieldSuplementalOxigenFlowRateMin;
    }

    public Integer getFieldOxigenTherapyAdministrationMode() {
        return this.fieldOxigenTherapyAdministrationMode;
    }

    public OxygenSaturationSummary fieldOxigenTherapyAdministrationMode(Integer fieldOxigenTherapyAdministrationMode) {
        this.setFieldOxigenTherapyAdministrationMode(fieldOxigenTherapyAdministrationMode);
        return this;
    }

    public void setFieldOxigenTherapyAdministrationMode(Integer fieldOxigenTherapyAdministrationMode) {
        this.fieldOxigenTherapyAdministrationMode = fieldOxigenTherapyAdministrationMode;
    }

    public Integer getFieldOxigenSaturationMode() {
        return this.fieldOxigenSaturationMode;
    }

    public OxygenSaturationSummary fieldOxigenSaturationMode(Integer fieldOxigenSaturationMode) {
        this.setFieldOxigenSaturationMode(fieldOxigenSaturationMode);
        return this;
    }

    public void setFieldOxigenSaturationMode(Integer fieldOxigenSaturationMode) {
        this.fieldOxigenSaturationMode = fieldOxigenSaturationMode;
    }

    public Integer getFieldOxigenSaturationMeasurementMethod() {
        return this.fieldOxigenSaturationMeasurementMethod;
    }

    public OxygenSaturationSummary fieldOxigenSaturationMeasurementMethod(Integer fieldOxigenSaturationMeasurementMethod) {
        this.setFieldOxigenSaturationMeasurementMethod(fieldOxigenSaturationMeasurementMethod);
        return this;
    }

    public void setFieldOxigenSaturationMeasurementMethod(Integer fieldOxigenSaturationMeasurementMethod) {
        this.fieldOxigenSaturationMeasurementMethod = fieldOxigenSaturationMeasurementMethod;
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    public OxygenSaturationSummary endTime(Instant endTime) {
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
        if (!(o instanceof OxygenSaturationSummary)) {
            return false;
        }
        return id != null && id.equals(((OxygenSaturationSummary) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OxygenSaturationSummary{" +
            "id=" + getId() +
            ", usuarioId='" + getUsuarioId() + "'" +
            ", empresaId='" + getEmpresaId() + "'" +
            ", fieldOxigenSaturationAverage=" + getFieldOxigenSaturationAverage() +
            ", fieldOxigenSaturationMax=" + getFieldOxigenSaturationMax() +
            ", fieldOxigenSaturationMin=" + getFieldOxigenSaturationMin() +
            ", fieldSuplementalOxigenFlowRateAverage=" + getFieldSuplementalOxigenFlowRateAverage() +
            ", fieldSuplementalOxigenFlowRateMax=" + getFieldSuplementalOxigenFlowRateMax() +
            ", fieldSuplementalOxigenFlowRateMin=" + getFieldSuplementalOxigenFlowRateMin() +
            ", fieldOxigenTherapyAdministrationMode=" + getFieldOxigenTherapyAdministrationMode() +
            ", fieldOxigenSaturationMode=" + getFieldOxigenSaturationMode() +
            ", fieldOxigenSaturationMeasurementMethod=" + getFieldOxigenSaturationMeasurementMethod() +
            ", endTime='" + getEndTime() + "'" +
            "}";
    }
}
