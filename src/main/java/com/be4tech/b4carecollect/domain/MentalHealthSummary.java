package com.be4tech.b4carecollect.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MentalHealthSummary.
 */
@Entity
@Table(name = "mental_health_summary")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MentalHealthSummary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "usuario_id")
    private String usuarioId;

    @Column(name = "empresa_id")
    private String empresaId;

    @Column(name = "emotion_descrip_main")
    private String emotionDescripMain;

    @Column(name = "emotion_descrip_second")
    private String emotionDescripSecond;

    @Column(name = "field_mental_health_average")
    private Float fieldMentalHealthAverage;

    @Column(name = "field_mental_health_max")
    private Float fieldMentalHealthMax;

    @Column(name = "field_mental_health_min")
    private Float fieldMentalHealthMin;

    @Column(name = "score_mental_risk")
    private Float scoreMentalRisk;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public MentalHealthSummary id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return this.usuarioId;
    }

    public MentalHealthSummary usuarioId(String usuarioId) {
        this.setUsuarioId(usuarioId);
        return this;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEmpresaId() {
        return this.empresaId;
    }

    public MentalHealthSummary empresaId(String empresaId) {
        this.setEmpresaId(empresaId);
        return this;
    }

    public void setEmpresaId(String empresaId) {
        this.empresaId = empresaId;
    }

    public String getEmotionDescripMain() {
        return this.emotionDescripMain;
    }

    public MentalHealthSummary emotionDescripMain(String emotionDescripMain) {
        this.setEmotionDescripMain(emotionDescripMain);
        return this;
    }

    public void setEmotionDescripMain(String emotionDescripMain) {
        this.emotionDescripMain = emotionDescripMain;
    }

    public String getEmotionDescripSecond() {
        return this.emotionDescripSecond;
    }

    public MentalHealthSummary emotionDescripSecond(String emotionDescripSecond) {
        this.setEmotionDescripSecond(emotionDescripSecond);
        return this;
    }

    public void setEmotionDescripSecond(String emotionDescripSecond) {
        this.emotionDescripSecond = emotionDescripSecond;
    }

    public Float getFieldMentalHealthAverage() {
        return this.fieldMentalHealthAverage;
    }

    public MentalHealthSummary fieldMentalHealthAverage(Float fieldMentalHealthAverage) {
        this.setFieldMentalHealthAverage(fieldMentalHealthAverage);
        return this;
    }

    public void setFieldMentalHealthAverage(Float fieldMentalHealthAverage) {
        this.fieldMentalHealthAverage = fieldMentalHealthAverage;
    }

    public Float getFieldMentalHealthMax() {
        return this.fieldMentalHealthMax;
    }

    public MentalHealthSummary fieldMentalHealthMax(Float fieldMentalHealthMax) {
        this.setFieldMentalHealthMax(fieldMentalHealthMax);
        return this;
    }

    public void setFieldMentalHealthMax(Float fieldMentalHealthMax) {
        this.fieldMentalHealthMax = fieldMentalHealthMax;
    }

    public Float getFieldMentalHealthMin() {
        return this.fieldMentalHealthMin;
    }

    public MentalHealthSummary fieldMentalHealthMin(Float fieldMentalHealthMin) {
        this.setFieldMentalHealthMin(fieldMentalHealthMin);
        return this;
    }

    public void setFieldMentalHealthMin(Float fieldMentalHealthMin) {
        this.fieldMentalHealthMin = fieldMentalHealthMin;
    }

    public Float getScoreMentalRisk() {
        return this.scoreMentalRisk;
    }

    public MentalHealthSummary scoreMentalRisk(Float scoreMentalRisk) {
        this.setScoreMentalRisk(scoreMentalRisk);
        return this;
    }

    public void setScoreMentalRisk(Float scoreMentalRisk) {
        this.scoreMentalRisk = scoreMentalRisk;
    }

    public Instant getStartTime() {
        return this.startTime;
    }

    public MentalHealthSummary startTime(Instant startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    public MentalHealthSummary endTime(Instant endTime) {
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
        if (!(o instanceof MentalHealthSummary)) {
            return false;
        }
        return id != null && id.equals(((MentalHealthSummary) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MentalHealthSummary{" +
            "id=" + getId() +
            ", usuarioId='" + getUsuarioId() + "'" +
            ", empresaId='" + getEmpresaId() + "'" +
            ", emotionDescripMain='" + getEmotionDescripMain() + "'" +
            ", emotionDescripSecond='" + getEmotionDescripSecond() + "'" +
            ", fieldMentalHealthAverage=" + getFieldMentalHealthAverage() +
            ", fieldMentalHealthMax=" + getFieldMentalHealthMax() +
            ", fieldMentalHealthMin=" + getFieldMentalHealthMin() +
            ", scoreMentalRisk=" + getScoreMentalRisk() +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            "}";
    }
}
