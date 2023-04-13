package com.be4tech.b4carecollect.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FuntionalIndex.
 */
@Entity
@Table(name = "funtional_index")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FuntionalIndex implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "usuario_id")
    private String usuarioId;

    @Column(name = "empresa_id")
    private String empresaId;

    @Column(name = "body_health_score")
    private Integer bodyHealthScore;

    @Column(name = "mental_health_score")
    private Integer mentalHealthScore;

    @Column(name = "sleep_health_score")
    private Integer sleepHealthScore;

    @Column(name = "funtional_index")
    private Integer funtionalIndex;

    @Column(name = "alarm_risk_score")
    private Integer alarmRiskScore;

    @Column(name = "start_time")
    private Instant startTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public FuntionalIndex id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return this.usuarioId;
    }

    public FuntionalIndex usuarioId(String usuarioId) {
        this.setUsuarioId(usuarioId);
        return this;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEmpresaId() {
        return this.empresaId;
    }

    public FuntionalIndex empresaId(String empresaId) {
        this.setEmpresaId(empresaId);
        return this;
    }

    public void setEmpresaId(String empresaId) {
        this.empresaId = empresaId;
    }

    public Integer getBodyHealthScore() {
        return this.bodyHealthScore;
    }

    public FuntionalIndex bodyHealthScore(Integer bodyHealthScore) {
        this.setBodyHealthScore(bodyHealthScore);
        return this;
    }

    public void setBodyHealthScore(Integer bodyHealthScore) {
        this.bodyHealthScore = bodyHealthScore;
    }

    public Integer getMentalHealthScore() {
        return this.mentalHealthScore;
    }

    public FuntionalIndex mentalHealthScore(Integer mentalHealthScore) {
        this.setMentalHealthScore(mentalHealthScore);
        return this;
    }

    public void setMentalHealthScore(Integer mentalHealthScore) {
        this.mentalHealthScore = mentalHealthScore;
    }

    public Integer getSleepHealthScore() {
        return this.sleepHealthScore;
    }

    public FuntionalIndex sleepHealthScore(Integer sleepHealthScore) {
        this.setSleepHealthScore(sleepHealthScore);
        return this;
    }

    public void setSleepHealthScore(Integer sleepHealthScore) {
        this.sleepHealthScore = sleepHealthScore;
    }

    public Integer getFuntionalIndex() {
        return this.funtionalIndex;
    }

    public FuntionalIndex funtionalIndex(Integer funtionalIndex) {
        this.setFuntionalIndex(funtionalIndex);
        return this;
    }

    public void setFuntionalIndex(Integer funtionalIndex) {
        this.funtionalIndex = funtionalIndex;
    }

    public Integer getAlarmRiskScore() {
        return this.alarmRiskScore;
    }

    public FuntionalIndex alarmRiskScore(Integer alarmRiskScore) {
        this.setAlarmRiskScore(alarmRiskScore);
        return this;
    }

    public void setAlarmRiskScore(Integer alarmRiskScore) {
        this.alarmRiskScore = alarmRiskScore;
    }

    public Instant getStartTime() {
        return this.startTime;
    }

    public FuntionalIndex startTime(Instant startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FuntionalIndex)) {
            return false;
        }
        return id != null && id.equals(((FuntionalIndex) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FuntionalIndex{" +
            "id=" + getId() +
            ", usuarioId='" + getUsuarioId() + "'" +
            ", empresaId='" + getEmpresaId() + "'" +
            ", bodyHealthScore=" + getBodyHealthScore() +
            ", mentalHealthScore=" + getMentalHealthScore() +
            ", sleepHealthScore=" + getSleepHealthScore() +
            ", funtionalIndex=" + getFuntionalIndex() +
            ", alarmRiskScore=" + getAlarmRiskScore() +
            ", startTime='" + getStartTime() + "'" +
            "}";
    }
}
