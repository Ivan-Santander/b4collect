package com.be4tech.b4carecollect.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.be4tech.b4carecollect.domain.BloodGlucoseSummary} entity. This class is used
 * in {@link com.be4tech.b4carecollect.web.rest.BloodGlucoseSummaryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /blood-glucose-summaries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BloodGlucoseSummaryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private StringFilter usuarioId;

    private StringFilter empresaId;

    private FloatFilter fieldAverage;

    private FloatFilter fieldMax;

    private FloatFilter fieldMin;

    private IntegerFilter intervalFood;

    private IntegerFilter relationTemporalSleep;

    private IntegerFilter sampleSource;

    private InstantFilter startTime;

    private InstantFilter endTime;

    private Boolean distinct;

    public BloodGlucoseSummaryCriteria() {}

    public BloodGlucoseSummaryCriteria(BloodGlucoseSummaryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.fieldAverage = other.fieldAverage == null ? null : other.fieldAverage.copy();
        this.fieldMax = other.fieldMax == null ? null : other.fieldMax.copy();
        this.fieldMin = other.fieldMin == null ? null : other.fieldMin.copy();
        this.intervalFood = other.intervalFood == null ? null : other.intervalFood.copy();
        this.relationTemporalSleep = other.relationTemporalSleep == null ? null : other.relationTemporalSleep.copy();
        this.sampleSource = other.sampleSource == null ? null : other.sampleSource.copy();
        this.startTime = other.startTime == null ? null : other.startTime.copy();
        this.endTime = other.endTime == null ? null : other.endTime.copy();
        this.distinct = other.distinct;
    }

    @Override
    public BloodGlucoseSummaryCriteria copy() {
        return new BloodGlucoseSummaryCriteria(this);
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

    public FloatFilter getFieldAverage() {
        return fieldAverage;
    }

    public FloatFilter fieldAverage() {
        if (fieldAverage == null) {
            fieldAverage = new FloatFilter();
        }
        return fieldAverage;
    }

    public void setFieldAverage(FloatFilter fieldAverage) {
        this.fieldAverage = fieldAverage;
    }

    public FloatFilter getFieldMax() {
        return fieldMax;
    }

    public FloatFilter fieldMax() {
        if (fieldMax == null) {
            fieldMax = new FloatFilter();
        }
        return fieldMax;
    }

    public void setFieldMax(FloatFilter fieldMax) {
        this.fieldMax = fieldMax;
    }

    public FloatFilter getFieldMin() {
        return fieldMin;
    }

    public FloatFilter fieldMin() {
        if (fieldMin == null) {
            fieldMin = new FloatFilter();
        }
        return fieldMin;
    }

    public void setFieldMin(FloatFilter fieldMin) {
        this.fieldMin = fieldMin;
    }

    public IntegerFilter getIntervalFood() {
        return intervalFood;
    }

    public IntegerFilter intervalFood() {
        if (intervalFood == null) {
            intervalFood = new IntegerFilter();
        }
        return intervalFood;
    }

    public void setIntervalFood(IntegerFilter intervalFood) {
        this.intervalFood = intervalFood;
    }

    public IntegerFilter getRelationTemporalSleep() {
        return relationTemporalSleep;
    }

    public IntegerFilter relationTemporalSleep() {
        if (relationTemporalSleep == null) {
            relationTemporalSleep = new IntegerFilter();
        }
        return relationTemporalSleep;
    }

    public void setRelationTemporalSleep(IntegerFilter relationTemporalSleep) {
        this.relationTemporalSleep = relationTemporalSleep;
    }

    public IntegerFilter getSampleSource() {
        return sampleSource;
    }

    public IntegerFilter sampleSource() {
        if (sampleSource == null) {
            sampleSource = new IntegerFilter();
        }
        return sampleSource;
    }

    public void setSampleSource(IntegerFilter sampleSource) {
        this.sampleSource = sampleSource;
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
        final BloodGlucoseSummaryCriteria that = (BloodGlucoseSummaryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(fieldAverage, that.fieldAverage) &&
            Objects.equals(fieldMax, that.fieldMax) &&
            Objects.equals(fieldMin, that.fieldMin) &&
            Objects.equals(intervalFood, that.intervalFood) &&
            Objects.equals(relationTemporalSleep, that.relationTemporalSleep) &&
            Objects.equals(sampleSource, that.sampleSource) &&
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
            fieldAverage,
            fieldMax,
            fieldMin,
            intervalFood,
            relationTemporalSleep,
            sampleSource,
            startTime,
            endTime,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BloodGlucoseSummaryCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            (fieldAverage != null ? "fieldAverage=" + fieldAverage + ", " : "") +
            (fieldMax != null ? "fieldMax=" + fieldMax + ", " : "") +
            (fieldMin != null ? "fieldMin=" + fieldMin + ", " : "") +
            (intervalFood != null ? "intervalFood=" + intervalFood + ", " : "") +
            (relationTemporalSleep != null ? "relationTemporalSleep=" + relationTemporalSleep + ", " : "") +
            (sampleSource != null ? "sampleSource=" + sampleSource + ", " : "") +
            (startTime != null ? "startTime=" + startTime + ", " : "") +
            (endTime != null ? "endTime=" + endTime + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
