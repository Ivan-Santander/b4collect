package com.be4tech.b4carecollect.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MentalHealth.
 */
@Entity
@Table(name = "mental_health")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MentalHealth implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "usuario_id")
    private String usuarioId;

    @Column(name = "empresa_id")
    private String empresaId;

    @Column(name = "emotion_description")
    private String emotionDescription;

    @Column(name = "emotion_value")
    private Float emotionValue;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "mental_health_score")
    private Float mentalHealthScore;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public MentalHealth id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return this.usuarioId;
    }

    public MentalHealth usuarioId(String usuarioId) {
        this.setUsuarioId(usuarioId);
        return this;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEmpresaId() {
        return this.empresaId;
    }

    public MentalHealth empresaId(String empresaId) {
        this.setEmpresaId(empresaId);
        return this;
    }

    public void setEmpresaId(String empresaId) {
        this.empresaId = empresaId;
    }

    public String getEmotionDescription() {
        return this.emotionDescription;
    }

    public MentalHealth emotionDescription(String emotionDescription) {
        this.setEmotionDescription(emotionDescription);
        return this;
    }

    public void setEmotionDescription(String emotionDescription) {
        this.emotionDescription = emotionDescription;
    }

    public Float getEmotionValue() {
        return this.emotionValue;
    }

    public MentalHealth emotionValue(Float emotionValue) {
        this.setEmotionValue(emotionValue);
        return this;
    }

    public void setEmotionValue(Float emotionValue) {
        this.emotionValue = emotionValue;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public MentalHealth startDate(Instant startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public MentalHealth endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Float getMentalHealthScore() {
        return this.mentalHealthScore;
    }

    public MentalHealth mentalHealthScore(Float mentalHealthScore) {
        this.setMentalHealthScore(mentalHealthScore);
        return this;
    }

    public void setMentalHealthScore(Float mentalHealthScore) {
        this.mentalHealthScore = mentalHealthScore;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MentalHealth)) {
            return false;
        }
        return id != null && id.equals(((MentalHealth) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MentalHealth{" +
            "id=" + getId() +
            ", usuarioId='" + getUsuarioId() + "'" +
            ", empresaId='" + getEmpresaId() + "'" +
            ", emotionDescription='" + getEmotionDescription() + "'" +
            ", emotionValue=" + getEmotionValue() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", mentalHealthScore=" + getMentalHealthScore() +
            "}";
    }
}
