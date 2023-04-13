package com.be4tech.b4carecollect.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.be4tech.b4carecollect.domain.MentalHealthSummary} entity. This class is used
 * in {@link com.be4tech.b4carecollect.web.rest.MentalHealthSummaryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /mental-health-summaries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MentalHealthSummaryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private StringFilter usuarioId;

    private StringFilter empresaId;

    private StringFilter emotionDescripMain;

    private StringFilter emotionDescripSecond;

    private FloatFilter fieldMentalHealthAverage;

    private FloatFilter fieldMentalHealthMax;

    private FloatFilter fieldMentalHealthMin;

    private FloatFilter scoreMentalRisk;

    private InstantFilter startTime;

    private InstantFilter endTime;

    private Boolean distinct;

    public MentalHealthSummaryCriteria() {}

    public MentalHealthSummaryCriteria(MentalHealthSummaryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.emotionDescripMain = other.emotionDescripMain == null ? null : other.emotionDescripMain.copy();
        this.emotionDescripSecond = other.emotionDescripSecond == null ? null : other.emotionDescripSecond.copy();
        this.fieldMentalHealthAverage = other.fieldMentalHealthAverage == null ? null : other.fieldMentalHealthAverage.copy();
        this.fieldMentalHealthMax = other.fieldMentalHealthMax == null ? null : other.fieldMentalHealthMax.copy();
        this.fieldMentalHealthMin = other.fieldMentalHealthMin == null ? null : other.fieldMentalHealthMin.copy();
        this.scoreMentalRisk = other.scoreMentalRisk == null ? null : other.scoreMentalRisk.copy();
        this.startTime = other.startTime == null ? null : other.startTime.copy();
        this.endTime = other.endTime == null ? null : other.endTime.copy();
        this.distinct = other.distinct;
    }

    @Override
    public MentalHealthSummaryCriteria copy() {
        return new MentalHealthSummaryCriteria(this);
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

    public StringFilter getEmotionDescripMain() {
        return emotionDescripMain;
    }

    public StringFilter emotionDescripMain() {
        if (emotionDescripMain == null) {
            emotionDescripMain = new StringFilter();
        }
        return emotionDescripMain;
    }

    public void setEmotionDescripMain(StringFilter emotionDescripMain) {
        this.emotionDescripMain = emotionDescripMain;
    }

    public StringFilter getEmotionDescripSecond() {
        return emotionDescripSecond;
    }

    public StringFilter emotionDescripSecond() {
        if (emotionDescripSecond == null) {
            emotionDescripSecond = new StringFilter();
        }
        return emotionDescripSecond;
    }

    public void setEmotionDescripSecond(StringFilter emotionDescripSecond) {
        this.emotionDescripSecond = emotionDescripSecond;
    }

    public FloatFilter getFieldMentalHealthAverage() {
        return fieldMentalHealthAverage;
    }

    public FloatFilter fieldMentalHealthAverage() {
        if (fieldMentalHealthAverage == null) {
            fieldMentalHealthAverage = new FloatFilter();
        }
        return fieldMentalHealthAverage;
    }

    public void setFieldMentalHealthAverage(FloatFilter fieldMentalHealthAverage) {
        this.fieldMentalHealthAverage = fieldMentalHealthAverage;
    }

    public FloatFilter getFieldMentalHealthMax() {
        return fieldMentalHealthMax;
    }

    public FloatFilter fieldMentalHealthMax() {
        if (fieldMentalHealthMax == null) {
            fieldMentalHealthMax = new FloatFilter();
        }
        return fieldMentalHealthMax;
    }

    public void setFieldMentalHealthMax(FloatFilter fieldMentalHealthMax) {
        this.fieldMentalHealthMax = fieldMentalHealthMax;
    }

    public FloatFilter getFieldMentalHealthMin() {
        return fieldMentalHealthMin;
    }

    public FloatFilter fieldMentalHealthMin() {
        if (fieldMentalHealthMin == null) {
            fieldMentalHealthMin = new FloatFilter();
        }
        return fieldMentalHealthMin;
    }

    public void setFieldMentalHealthMin(FloatFilter fieldMentalHealthMin) {
        this.fieldMentalHealthMin = fieldMentalHealthMin;
    }

    public FloatFilter getScoreMentalRisk() {
        return scoreMentalRisk;
    }

    public FloatFilter scoreMentalRisk() {
        if (scoreMentalRisk == null) {
            scoreMentalRisk = new FloatFilter();
        }
        return scoreMentalRisk;
    }

    public void setScoreMentalRisk(FloatFilter scoreMentalRisk) {
        this.scoreMentalRisk = scoreMentalRisk;
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

    public InstantFilter getEndTime() {
        return endTime;
    }

    public InstantFilter endTime() {
        if (endTime == null) {
            endTime = new InstantFilter();
        }
        return endTime;
    }

    public void setEndTime(InstantFilter endTime) {
        this.endTime = endTime;
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
        final MentalHealthSummaryCriteria that = (MentalHealthSummaryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(emotionDescripMain, that.emotionDescripMain) &&
            Objects.equals(emotionDescripSecond, that.emotionDescripSecond) &&
            Objects.equals(fieldMentalHealthAverage, that.fieldMentalHealthAverage) &&
            Objects.equals(fieldMentalHealthMax, that.fieldMentalHealthMax) &&
            Objects.equals(fieldMentalHealthMin, that.fieldMentalHealthMin) &&
            Objects.equals(scoreMentalRisk, that.scoreMentalRisk) &&
            Objects.equals(startTime, that.startTime) &&
            Objects.equals(endTime, that.endTime) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            usuarioId,
            empresaId,
            emotionDescripMain,
            emotionDescripSecond,
            fieldMentalHealthAverage,
            fieldMentalHealthMax,
            fieldMentalHealthMin,
            scoreMentalRisk,
            startTime,
            endTime,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MentalHealthSummaryCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            (emotionDescripMain != null ? "emotionDescripMain=" + emotionDescripMain + ", " : "") +
            (emotionDescripSecond != null ? "emotionDescripSecond=" + emotionDescripSecond + ", " : "") +
            (fieldMentalHealthAverage != null ? "fieldMentalHealthAverage=" + fieldMentalHealthAverage + ", " : "") +
            (fieldMentalHealthMax != null ? "fieldMentalHealthMax=" + fieldMentalHealthMax + ", " : "") +
            (fieldMentalHealthMin != null ? "fieldMentalHealthMin=" + fieldMentalHealthMin + ", " : "") +
            (scoreMentalRisk != null ? "scoreMentalRisk=" + scoreMentalRisk + ", " : "") +
            (startTime != null ? "startTime=" + startTime + ", " : "") +
            (endTime != null ? "endTime=" + endTime + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
