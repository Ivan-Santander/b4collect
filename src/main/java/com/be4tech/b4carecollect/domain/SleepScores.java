package com.be4tech.b4carecollect.domain;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SleepScores.
 */
@Entity
@Table(name = "sleep_scores")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SleepScores implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "usuario_id")
    private String usuarioId;

    @Column(name = "empresa_id")
    private String empresaId;

    @Column(name = "sleep_quality_rating_score")
    private Integer sleepQualityRatingScore;

    @Column(name = "sleep_efficiency_score")
    private Integer sleepEfficiencyScore;

    @Column(name = "sleep_gooal_seconds_score")
    private Integer sleepGooalSecondsScore;

    @Column(name = "sleep_continuity_score")
    private Integer sleepContinuityScore;

    @Column(name = "sleep_continuity_rating")
    private Integer sleepContinuityRating;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public SleepScores id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return this.usuarioId;
    }

    public SleepScores usuarioId(String usuarioId) {
        this.setUsuarioId(usuarioId);
        return this;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEmpresaId() {
        return this.empresaId;
    }

    public SleepScores empresaId(String empresaId) {
        this.setEmpresaId(empresaId);
        return this;
    }

    public void setEmpresaId(String empresaId) {
        this.empresaId = empresaId;
    }

    public Integer getSleepQualityRatingScore() {
        return this.sleepQualityRatingScore;
    }

    public SleepScores sleepQualityRatingScore(Integer sleepQualityRatingScore) {
        this.setSleepQualityRatingScore(sleepQualityRatingScore);
        return this;
    }

    public void setSleepQualityRatingScore(Integer sleepQualityRatingScore) {
        this.sleepQualityRatingScore = sleepQualityRatingScore;
    }

    public Integer getSleepEfficiencyScore() {
        return this.sleepEfficiencyScore;
    }

    public SleepScores sleepEfficiencyScore(Integer sleepEfficiencyScore) {
        this.setSleepEfficiencyScore(sleepEfficiencyScore);
        return this;
    }

    public void setSleepEfficiencyScore(Integer sleepEfficiencyScore) {
        this.sleepEfficiencyScore = sleepEfficiencyScore;
    }

    public Integer getSleepGooalSecondsScore() {
        return this.sleepGooalSecondsScore;
    }

    public SleepScores sleepGooalSecondsScore(Integer sleepGooalSecondsScore) {
        this.setSleepGooalSecondsScore(sleepGooalSecondsScore);
        return this;
    }

    public void setSleepGooalSecondsScore(Integer sleepGooalSecondsScore) {
        this.sleepGooalSecondsScore = sleepGooalSecondsScore;
    }

    public Integer getSleepContinuityScore() {
        return this.sleepContinuityScore;
    }

    public SleepScores sleepContinuityScore(Integer sleepContinuityScore) {
        this.setSleepContinuityScore(sleepContinuityScore);
        return this;
    }

    public void setSleepContinuityScore(Integer sleepContinuityScore) {
        this.sleepContinuityScore = sleepContinuityScore;
    }

    public Integer getSleepContinuityRating() {
        return this.sleepContinuityRating;
    }

    public SleepScores sleepContinuityRating(Integer sleepContinuityRating) {
        this.setSleepContinuityRating(sleepContinuityRating);
        return this;
    }

    public void setSleepContinuityRating(Integer sleepContinuityRating) {
        this.sleepContinuityRating = sleepContinuityRating;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SleepScores)) {
            return false;
        }
        return id != null && id.equals(((SleepScores) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SleepScores{" +
            "id=" + getId() +
            ", usuarioId='" + getUsuarioId() + "'" +
            ", empresaId='" + getEmpresaId() + "'" +
            ", sleepQualityRatingScore=" + getSleepQualityRatingScore() +
            ", sleepEfficiencyScore=" + getSleepEfficiencyScore() +
            ", sleepGooalSecondsScore=" + getSleepGooalSecondsScore() +
            ", sleepContinuityScore=" + getSleepContinuityScore() +
            ", sleepContinuityRating=" + getSleepContinuityRating() +
            "}";
    }
}
