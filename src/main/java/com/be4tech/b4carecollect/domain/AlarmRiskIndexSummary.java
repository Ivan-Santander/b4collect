package com.be4tech.b4carecollect.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AlarmRiskIndexSummary.
 */
@Entity
@Table(name = "alarm_risk_index_summary")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AlarmRiskIndexSummary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "usuario_id")
    private String usuarioId;

    @Column(name = "empresa_id")
    private String empresaId;

    @Column(name = "field_alarm_risk_average")
    private Float fieldAlarmRiskAverage;

    @Column(name = "field_alarm_risk_max")
    private Float fieldAlarmRiskMax;

    @Column(name = "field_alarm_risk_min")
    private Float fieldAlarmRiskMin;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public AlarmRiskIndexSummary id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return this.usuarioId;
    }

    public AlarmRiskIndexSummary usuarioId(String usuarioId) {
        this.setUsuarioId(usuarioId);
        return this;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEmpresaId() {
        return this.empresaId;
    }

    public AlarmRiskIndexSummary empresaId(String empresaId) {
        this.setEmpresaId(empresaId);
        return this;
    }

    public void setEmpresaId(String empresaId) {
        this.empresaId = empresaId;
    }

    public Float getFieldAlarmRiskAverage() {
        return this.fieldAlarmRiskAverage;
    }

    public AlarmRiskIndexSummary fieldAlarmRiskAverage(Float fieldAlarmRiskAverage) {
        this.setFieldAlarmRiskAverage(fieldAlarmRiskAverage);
        return this;
    }

    public void setFieldAlarmRiskAverage(Float fieldAlarmRiskAverage) {
        this.fieldAlarmRiskAverage = fieldAlarmRiskAverage;
    }

    public Float getFieldAlarmRiskMax() {
        return this.fieldAlarmRiskMax;
    }

    public AlarmRiskIndexSummary fieldAlarmRiskMax(Float fieldAlarmRiskMax) {
        this.setFieldAlarmRiskMax(fieldAlarmRiskMax);
        return this;
    }

    public void setFieldAlarmRiskMax(Float fieldAlarmRiskMax) {
        this.fieldAlarmRiskMax = fieldAlarmRiskMax;
    }

    public Float getFieldAlarmRiskMin() {
        return this.fieldAlarmRiskMin;
    }

    public AlarmRiskIndexSummary fieldAlarmRiskMin(Float fieldAlarmRiskMin) {
        this.setFieldAlarmRiskMin(fieldAlarmRiskMin);
        return this;
    }

    public void setFieldAlarmRiskMin(Float fieldAlarmRiskMin) {
        this.fieldAlarmRiskMin = fieldAlarmRiskMin;
    }

    public Instant getStartTime() {
        return this.startTime;
    }

    public AlarmRiskIndexSummary startTime(Instant startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    public AlarmRiskIndexSummary endTime(Instant endTime) {
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
        if (!(o instanceof AlarmRiskIndexSummary)) {
            return false;
        }
        return id != null && id.equals(((AlarmRiskIndexSummary) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlarmRiskIndexSummary{" +
            "id=" + getId() +
            ", usuarioId='" + getUsuarioId() + "'" +
            ", empresaId='" + getEmpresaId() + "'" +
            ", fieldAlarmRiskAverage=" + getFieldAlarmRiskAverage() +
            ", fieldAlarmRiskMax=" + getFieldAlarmRiskMax() +
            ", fieldAlarmRiskMin=" + getFieldAlarmRiskMin() +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            "}";
    }
}
