package com.be4tech.b4carecollect.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.be4tech.b4carecollect.domain.FuntionalIndex} entity. This class is used
 * in {@link com.be4tech.b4carecollect.web.rest.FuntionalIndexResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /funtional-indices?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FuntionalIndexCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private StringFilter usuarioId;

    private StringFilter empresaId;

    private IntegerFilter bodyHealthScore;

    private IntegerFilter mentalHealthScore;

    private IntegerFilter sleepHealthScore;

    private IntegerFilter funtionalIndex;

    private IntegerFilter alarmRiskScore;

    private InstantFilter startTime;

    private Boolean distinct;

    public FuntionalIndexCriteria() {}

    public FuntionalIndexCriteria(FuntionalIndexCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.bodyHealthScore = other.bodyHealthScore == null ? null : other.bodyHealthScore.copy();
        this.mentalHealthScore = other.mentalHealthScore == null ? null : other.mentalHealthScore.copy();
        this.sleepHealthScore = other.sleepHealthScore == null ? null : other.sleepHealthScore.copy();
        this.funtionalIndex = other.funtionalIndex == null ? null : other.funtionalIndex.copy();
        this.alarmRiskScore = other.alarmRiskScore == null ? null : other.alarmRiskScore.copy();
        this.startTime = other.startTime == null ? null : other.startTime.copy();
        this.distinct = other.distinct;
    }

    @Override
    public FuntionalIndexCriteria copy() {
        return new FuntionalIndexCriteria(this);
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

    public IntegerFilter getBodyHealthScore() {
        return bodyHealthScore;
    }

    public IntegerFilter bodyHealthScore() {
        if (bodyHealthScore == null) {
            bodyHealthScore = new IntegerFilter();
        }
        return bodyHealthScore;
    }

    public void setBodyHealthScore(IntegerFilter bodyHealthScore) {
        this.bodyHealthScore = bodyHealthScore;
    }

    public IntegerFilter getMentalHealthScore() {
        return mentalHealthScore;
    }

    public IntegerFilter mentalHealthScore() {
        if (mentalHealthScore == null) {
            mentalHealthScore = new IntegerFilter();
        }
        return mentalHealthScore;
    }

    public void setMentalHealthScore(IntegerFilter mentalHealthScore) {
        this.mentalHealthScore = mentalHealthScore;
    }

    public IntegerFilter getSleepHealthScore() {
        return sleepHealthScore;
    }

    public IntegerFilter sleepHealthScore() {
        if (sleepHealthScore == null) {
            sleepHealthScore = new IntegerFilter();
        }
        return sleepHealthScore;
    }

    public void setSleepHealthScore(IntegerFilter sleepHealthScore) {
        this.sleepHealthScore = sleepHealthScore;
    }

    public IntegerFilter getFuntionalIndex() {
        return funtionalIndex;
    }

    public IntegerFilter funtionalIndex() {
        if (funtionalIndex == null) {
            funtionalIndex = new IntegerFilter();
        }
        return funtionalIndex;
    }

    public void setFuntionalIndex(IntegerFilter funtionalIndex) {
        this.funtionalIndex = funtionalIndex;
    }

    public IntegerFilter getAlarmRiskScore() {
        return alarmRiskScore;
    }

    public IntegerFilter alarmRiskScore() {
        if (alarmRiskScore == null) {
            alarmRiskScore = new IntegerFilter();
        }
        return alarmRiskScore;
    }

    public void setAlarmRiskScore(IntegerFilter alarmRiskScore) {
        this.alarmRiskScore = alarmRiskScore;
    }

    public InstantFilter getStartTime() {
        return startTime;
    }

    public InstantFilter startTime() {
        if (startTime == null) {
            startTime = new InstantFilter();
        }
        return startTime;
    }

    public void setStartTime(InstantFilter startTime) {
        this.startTime = startTime;
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
        final FuntionalIndexCriteria that = (FuntionalIndexCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(bodyHealthScore, that.bodyHealthScore) &&
            Objects.equals(mentalHealthScore, that.mentalHealthScore) &&
            Objects.equals(sleepHealthScore, that.sleepHealthScore) &&
            Objects.equals(funtionalIndex, that.funtionalIndex) &&
            Objects.equals(alarmRiskScore, that.alarmRiskScore) &&
            Objects.equals(startTime, that.startTime) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            usuarioId,
            empresaId,
            bodyHealthScore,
            mentalHealthScore,
            sleepHealthScore,
            funtionalIndex,
            alarmRiskScore,
            startTime,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FuntionalIndexCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            (bodyHealthScore != null ? "bodyHealthScore=" + bodyHealthScore + ", " : "") +
            (mentalHealthScore != null ? "mentalHealthScore=" + mentalHealthScore + ", " : "") +
            (sleepHealthScore != null ? "sleepHealthScore=" + sleepHealthScore + ", " : "") +
            (funtionalIndex != null ? "funtionalIndex=" + funtionalIndex + ", " : "") +
            (alarmRiskScore != null ? "alarmRiskScore=" + alarmRiskScore + ", " : "") +
            (startTime != null ? "startTime=" + startTime + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
