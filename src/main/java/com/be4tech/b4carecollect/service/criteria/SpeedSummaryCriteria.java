package com.be4tech.b4carecollect.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.be4tech.b4carecollect.domain.SpeedSummary} entity. This class is used
 * in {@link com.be4tech.b4carecollect.web.rest.SpeedSummaryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /speed-summaries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SpeedSummaryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private StringFilter usuarioId;

    private StringFilter empresaId;

    private FloatFilter fieldAverage;

    private FloatFilter fieldMax;

    private FloatFilter fieldMin;

    private InstantFilter startTime;

    private InstantFilter endTime;

    private Boolean distinct;

    public SpeedSummaryCriteria() {}

    public SpeedSummaryCriteria(SpeedSummaryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.fieldAverage = other.fieldAverage == null ? null : other.fieldAverage.copy();
        this.fieldMax = other.fieldMax == null ? null : other.fieldMax.copy();
        this.fieldMin = other.fieldMin == null ? null : other.fieldMin.copy();
        this.startTime = other.startTime == null ? null : other.startTime.copy();
        this.endTime = other.endTime == null ? null : other.endTime.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SpeedSummaryCriteria copy() {
        return new SpeedSummaryCriteria(this);
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
        final SpeedSummaryCriteria that = (SpeedSummaryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(fieldAverage, that.fieldAverage) &&
            Objects.equals(fieldMax, that.fieldMax) &&
            Objects.equals(fieldMin, that.fieldMin) &&
            Objects.equals(startTime, that.startTime) &&
            Objects.equals(endTime, that.endTime) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usuarioId, empresaId, fieldAverage, fieldMax, fieldMin, startTime, endTime, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SpeedSummaryCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            (fieldAverage != null ? "fieldAverage=" + fieldAverage + ", " : "") +
            (fieldMax != null ? "fieldMax=" + fieldMax + ", " : "") +
            (fieldMin != null ? "fieldMin=" + fieldMin + ", " : "") +
            (startTime != null ? "startTime=" + startTime + ", " : "") +
            (endTime != null ? "endTime=" + endTime + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
