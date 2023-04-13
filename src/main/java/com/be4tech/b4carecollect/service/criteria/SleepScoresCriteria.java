package com.be4tech.b4carecollect.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.be4tech.b4carecollect.domain.SleepScores} entity. This class is used
 * in {@link com.be4tech.b4carecollect.web.rest.SleepScoresResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sleep-scores?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SleepScoresCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private StringFilter usuarioId;

    private StringFilter empresaId;

    private IntegerFilter sleepQualityRatingScore;

    private IntegerFilter sleepEfficiencyScore;

    private IntegerFilter sleepGooalSecondsScore;

    private IntegerFilter sleepContinuityScore;

    private IntegerFilter sleepContinuityRating;

    private Boolean distinct;

    public SleepScoresCriteria() {}

    public SleepScoresCriteria(SleepScoresCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.sleepQualityRatingScore = other.sleepQualityRatingScore == null ? null : other.sleepQualityRatingScore.copy();
        this.sleepEfficiencyScore = other.sleepEfficiencyScore == null ? null : other.sleepEfficiencyScore.copy();
        this.sleepGooalSecondsScore = other.sleepGooalSecondsScore == null ? null : other.sleepGooalSecondsScore.copy();
        this.sleepContinuityScore = other.sleepContinuityScore == null ? null : other.sleepContinuityScore.copy();
        this.sleepContinuityRating = other.sleepContinuityRating == null ? null : other.sleepContinuityRating.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SleepScoresCriteria copy() {
        return new SleepScoresCriteria(this);
    }

    public UUIDFilter getId() {
        return id;
    }

    public UUIDFilter id() {
        if (id == null) {
            id = new UUIDFilter();
        }
        return id;
    }

    public void setId(UUIDFilter id) {
        this.id = id;
    }

    public StringFilter getUsuarioId() {
        return usuarioId;
    }

    public StringFilter usuarioId() {
        if (usuarioId == null) {
            usuarioId = new StringFilter();
        }
        return usuarioId;
    }

    public void setUsuarioId(StringFilter usuarioId) {
        this.usuarioId = usuarioId;
    }

    public StringFilter getEmpresaId() {
        return empresaId;
    }

    public StringFilter empresaId() {
        if (empresaId == null) {
            empresaId = new StringFilter();
        }
        return empresaId;
    }

    public void setEmpresaId(StringFilter empresaId) {
        this.empresaId = empresaId;
    }

    public IntegerFilter getSleepQualityRatingScore() {
        return sleepQualityRatingScore;
    }

    public IntegerFilter sleepQualityRatingScore() {
        if (sleepQualityRatingScore == null) {
            sleepQualityRatingScore = new IntegerFilter();
        }
        return sleepQualityRatingScore;
    }

    public void setSleepQualityRatingScore(IntegerFilter sleepQualityRatingScore) {
        this.sleepQualityRatingScore = sleepQualityRatingScore;
    }

    public IntegerFilter getSleepEfficiencyScore() {
        return sleepEfficiencyScore;
    }

    public IntegerFilter sleepEfficiencyScore() {
        if (sleepEfficiencyScore == null) {
            sleepEfficiencyScore = new IntegerFilter();
        }
        return sleepEfficiencyScore;
    }

    public void setSleepEfficiencyScore(IntegerFilter sleepEfficiencyScore) {
        this.sleepEfficiencyScore = sleepEfficiencyScore;
    }

    public IntegerFilter getSleepGooalSecondsScore() {
        return sleepGooalSecondsScore;
    }

    public IntegerFilter sleepGooalSecondsScore() {
        if (sleepGooalSecondsScore == null) {
            sleepGooalSecondsScore = new IntegerFilter();
        }
        return sleepGooalSecondsScore;
    }

    public void setSleepGooalSecondsScore(IntegerFilter sleepGooalSecondsScore) {
        this.sleepGooalSecondsScore = sleepGooalSecondsScore;
    }

    public IntegerFilter getSleepContinuityScore() {
        return sleepContinuityScore;
    }

    public IntegerFilter sleepContinuityScore() {
        if (sleepContinuityScore == null) {
            sleepContinuityScore = new IntegerFilter();
        }
        return sleepContinuityScore;
    }

    public void setSleepContinuityScore(IntegerFilter sleepContinuityScore) {
        this.sleepContinuityScore = sleepContinuityScore;
    }

    public IntegerFilter getSleepContinuityRating() {
        return sleepContinuityRating;
    }

    public IntegerFilter sleepContinuityRating() {
        if (sleepContinuityRating == null) {
            sleepContinuityRating = new IntegerFilter();
        }
        return sleepContinuityRating;
    }

    public void setSleepContinuityRating(IntegerFilter sleepContinuityRating) {
        this.sleepContinuityRating = sleepContinuityRating;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SleepScoresCriteria that = (SleepScoresCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(sleepQualityRatingScore, that.sleepQualityRatingScore) &&
            Objects.equals(sleepEfficiencyScore, that.sleepEfficiencyScore) &&
            Objects.equals(sleepGooalSecondsScore, that.sleepGooalSecondsScore) &&
            Objects.equals(sleepContinuityScore, that.sleepContinuityScore) &&
            Objects.equals(sleepContinuityRating, that.sleepContinuityRating) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            usuarioId,
            empresaId,
            sleepQualityRatingScore,
            sleepEfficiencyScore,
            sleepGooalSecondsScore,
            sleepContinuityScore,
            sleepContinuityRating,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SleepScoresCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            (sleepQualityRatingScore != null ? "sleepQualityRatingScore=" + sleepQualityRatingScore + ", " : "") +
            (sleepEfficiencyScore != null ? "sleepEfficiencyScore=" + sleepEfficiencyScore + ", " : "") +
            (sleepGooalSecondsScore != null ? "sleepGooalSecondsScore=" + sleepGooalSecondsScore + ", " : "") +
            (sleepContinuityScore != null ? "sleepContinuityScore=" + sleepContinuityScore + ", " : "") +
            (sleepContinuityRating != null ? "sleepContinuityRating=" + sleepContinuityRating + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
