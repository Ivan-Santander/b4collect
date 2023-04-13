package com.be4tech.b4carecollect.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.be4tech.b4carecollect.domain.FuntionalIndexSummary} entity. This class is used
 * in {@link com.be4tech.b4carecollect.web.rest.FuntionalIndexSummaryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /funtional-index-summaries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FuntionalIndexSummaryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private StringFilter usuarioId;

    private StringFilter empresaId;

    private FloatFilter fieldFuntionalIndexAverage;

    private FloatFilter fieldFuntionalIndexMax;

    private FloatFilter fieldFuntionalIndexMin;

    private InstantFilter startTime;

    private InstantFilter endTime;

    private Boolean distinct;

    public FuntionalIndexSummaryCriteria() {}

    public FuntionalIndexSummaryCriteria(FuntionalIndexSummaryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.fieldFuntionalIndexAverage = other.fieldFuntionalIndexAverage == null ? null : other.fieldFuntionalIndexAverage.copy();
        this.fieldFuntionalIndexMax = other.fieldFuntionalIndexMax == null ? null : other.fieldFuntionalIndexMax.copy();
        this.fieldFuntionalIndexMin = other.fieldFuntionalIndexMin == null ? null : other.fieldFuntionalIndexMin.copy();
        this.startTime = other.startTime == null ? null : other.startTime.copy();
        this.endTime = other.endTime == null ? null : other.endTime.copy();
        this.distinct = other.distinct;
    }

    @Override
    public FuntionalIndexSummaryCriteria copy() {
        return new FuntionalIndexSummaryCriteria(this);
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

    public FloatFilter getFieldFuntionalIndexAverage() {
        return fieldFuntionalIndexAverage;
    }

    public FloatFilter fieldFuntionalIndexAverage() {
        if (fieldFuntionalIndexAverage == null) {
            fieldFuntionalIndexAverage = new FloatFilter();
        }
        return fieldFuntionalIndexAverage;
    }

    public void setFieldFuntionalIndexAverage(FloatFilter fieldFuntionalIndexAverage) {
        this.fieldFuntionalIndexAverage = fieldFuntionalIndexAverage;
    }

    public FloatFilter getFieldFuntionalIndexMax() {
        return fieldFuntionalIndexMax;
    }

    public FloatFilter fieldFuntionalIndexMax() {
        if (fieldFuntionalIndexMax == null) {
            fieldFuntionalIndexMax = new FloatFilter();
        }
        return fieldFuntionalIndexMax;
    }

    public void setFieldFuntionalIndexMax(FloatFilter fieldFuntionalIndexMax) {
        this.fieldFuntionalIndexMax = fieldFuntionalIndexMax;
    }

    public FloatFilter getFieldFuntionalIndexMin() {
        return fieldFuntionalIndexMin;
    }

    public FloatFilter fieldFuntionalIndexMin() {
        if (fieldFuntionalIndexMin == null) {
            fieldFuntionalIndexMin = new FloatFilter();
        }
        return fieldFuntionalIndexMin;
    }

    public void setFieldFuntionalIndexMin(FloatFilter fieldFuntionalIndexMin) {
        this.fieldFuntionalIndexMin = fieldFuntionalIndexMin;
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
        final FuntionalIndexSummaryCriteria that = (FuntionalIndexSummaryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(fieldFuntionalIndexAverage, that.fieldFuntionalIndexAverage) &&
            Objects.equals(fieldFuntionalIndexMax, that.fieldFuntionalIndexMax) &&
            Objects.equals(fieldFuntionalIndexMin, that.fieldFuntionalIndexMin) &&
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
            fieldFuntionalIndexAverage,
            fieldFuntionalIndexMax,
            fieldFuntionalIndexMin,
            startTime,
            endTime,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FuntionalIndexSummaryCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            (fieldFuntionalIndexAverage != null ? "fieldFuntionalIndexAverage=" + fieldFuntionalIndexAverage + ", " : "") +
            (fieldFuntionalIndexMax != null ? "fieldFuntionalIndexMax=" + fieldFuntionalIndexMax + ", " : "") +
            (fieldFuntionalIndexMin != null ? "fieldFuntionalIndexMin=" + fieldFuntionalIndexMin + ", " : "") +
            (startTime != null ? "startTime=" + startTime + ", " : "") +
            (endTime != null ? "endTime=" + endTime + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
