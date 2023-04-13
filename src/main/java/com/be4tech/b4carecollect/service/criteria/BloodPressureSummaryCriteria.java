package com.be4tech.b4carecollect.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.be4tech.b4carecollect.domain.BloodPressureSummary} entity. This class is used
 * in {@link com.be4tech.b4carecollect.web.rest.BloodPressureSummaryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /blood-pressure-summaries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BloodPressureSummaryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private StringFilter usuarioId;

    private StringFilter empresaId;

    private FloatFilter fieldSistolicAverage;

    private FloatFilter fieldSistolicMax;

    private FloatFilter fieldSistolicMin;

    private FloatFilter fieldDiasatolicAverage;

    private FloatFilter fieldDiastolicMax;

    private FloatFilter fieldDiastolicMin;

    private IntegerFilter bodyPosition;

    private IntegerFilter measurementLocation;

    private InstantFilter startTime;

    private InstantFilter endTime;

    private Boolean distinct;

    public BloodPressureSummaryCriteria() {}

    public BloodPressureSummaryCriteria(BloodPressureSummaryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.fieldSistolicAverage = other.fieldSistolicAverage == null ? null : other.fieldSistolicAverage.copy();
        this.fieldSistolicMax = other.fieldSistolicMax == null ? null : other.fieldSistolicMax.copy();
        this.fieldSistolicMin = other.fieldSistolicMin == null ? null : other.fieldSistolicMin.copy();
        this.fieldDiasatolicAverage = other.fieldDiasatolicAverage == null ? null : other.fieldDiasatolicAverage.copy();
        this.fieldDiastolicMax = other.fieldDiastolicMax == null ? null : other.fieldDiastolicMax.copy();
        this.fieldDiastolicMin = other.fieldDiastolicMin == null ? null : other.fieldDiastolicMin.copy();
        this.bodyPosition = other.bodyPosition == null ? null : other.bodyPosition.copy();
        this.measurementLocation = other.measurementLocation == null ? null : other.measurementLocation.copy();
        this.startTime = other.startTime == null ? null : other.startTime.copy();
        this.endTime = other.endTime == null ? null : other.endTime.copy();
        this.distinct = other.distinct;
    }

    @Override
    public BloodPressureSummaryCriteria copy() {
        return new BloodPressureSummaryCriteria(this);
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

    public FloatFilter getFieldSistolicAverage() {
        return fieldSistolicAverage;
    }

    public FloatFilter fieldSistolicAverage() {
        if (fieldSistolicAverage == null) {
            fieldSistolicAverage = new FloatFilter();
        }
        return fieldSistolicAverage;
    }

    public void setFieldSistolicAverage(FloatFilter fieldSistolicAverage) {
        this.fieldSistolicAverage = fieldSistolicAverage;
    }

    public FloatFilter getFieldSistolicMax() {
        return fieldSistolicMax;
    }

    public FloatFilter fieldSistolicMax() {
        if (fieldSistolicMax == null) {
            fieldSistolicMax = new FloatFilter();
        }
        return fieldSistolicMax;
    }

    public void setFieldSistolicMax(FloatFilter fieldSistolicMax) {
        this.fieldSistolicMax = fieldSistolicMax;
    }

    public FloatFilter getFieldSistolicMin() {
        return fieldSistolicMin;
    }

    public FloatFilter fieldSistolicMin() {
        if (fieldSistolicMin == null) {
            fieldSistolicMin = new FloatFilter();
        }
        return fieldSistolicMin;
    }

    public void setFieldSistolicMin(FloatFilter fieldSistolicMin) {
        this.fieldSistolicMin = fieldSistolicMin;
    }

    public FloatFilter getFieldDiasatolicAverage() {
        return fieldDiasatolicAverage;
    }

    public FloatFilter fieldDiasatolicAverage() {
        if (fieldDiasatolicAverage == null) {
            fieldDiasatolicAverage = new FloatFilter();
        }
        return fieldDiasatolicAverage;
    }

    public void setFieldDiasatolicAverage(FloatFilter fieldDiasatolicAverage) {
        this.fieldDiasatolicAverage = fieldDiasatolicAverage;
    }

    public FloatFilter getFieldDiastolicMax() {
        return fieldDiastolicMax;
    }

    public FloatFilter fieldDiastolicMax() {
        if (fieldDiastolicMax == null) {
            fieldDiastolicMax = new FloatFilter();
        }
        return fieldDiastolicMax;
    }

    public void setFieldDiastolicMax(FloatFilter fieldDiastolicMax) {
        this.fieldDiastolicMax = fieldDiastolicMax;
    }

    public FloatFilter getFieldDiastolicMin() {
        return fieldDiastolicMin;
    }

    public FloatFilter fieldDiastolicMin() {
        if (fieldDiastolicMin == null) {
            fieldDiastolicMin = new FloatFilter();
        }
        return fieldDiastolicMin;
    }

    public void setFieldDiastolicMin(FloatFilter fieldDiastolicMin) {
        this.fieldDiastolicMin = fieldDiastolicMin;
    }

    public IntegerFilter getBodyPosition() {
        return bodyPosition;
    }

    public IntegerFilter bodyPosition() {
        if (bodyPosition == null) {
            bodyPosition = new IntegerFilter();
        }
        return bodyPosition;
    }

    public void setBodyPosition(IntegerFilter bodyPosition) {
        this.bodyPosition = bodyPosition;
    }

    public IntegerFilter getMeasurementLocation() {
        return measurementLocation;
    }

    public IntegerFilter measurementLocation() {
        if (measurementLocation == null) {
            measurementLocation = new IntegerFilter();
        }
        return measurementLocation;
    }

    public void setMeasurementLocation(IntegerFilter measurementLocation) {
        this.measurementLocation = measurementLocation;
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
        final BloodPressureSummaryCriteria that = (BloodPressureSummaryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(fieldSistolicAverage, that.fieldSistolicAverage) &&
            Objects.equals(fieldSistolicMax, that.fieldSistolicMax) &&
            Objects.equals(fieldSistolicMin, that.fieldSistolicMin) &&
            Objects.equals(fieldDiasatolicAverage, that.fieldDiasatolicAverage) &&
            Objects.equals(fieldDiastolicMax, that.fieldDiastolicMax) &&
            Objects.equals(fieldDiastolicMin, that.fieldDiastolicMin) &&
            Objects.equals(bodyPosition, that.bodyPosition) &&
            Objects.equals(measurementLocation, that.measurementLocation) &&
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
            fieldSistolicAverage,
            fieldSistolicMax,
            fieldSistolicMin,
            fieldDiasatolicAverage,
            fieldDiastolicMax,
            fieldDiastolicMin,
            bodyPosition,
            measurementLocation,
            startTime,
            endTime,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BloodPressureSummaryCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            (fieldSistolicAverage != null ? "fieldSistolicAverage=" + fieldSistolicAverage + ", " : "") +
            (fieldSistolicMax != null ? "fieldSistolicMax=" + fieldSistolicMax + ", " : "") +
            (fieldSistolicMin != null ? "fieldSistolicMin=" + fieldSistolicMin + ", " : "") +
            (fieldDiasatolicAverage != null ? "fieldDiasatolicAverage=" + fieldDiasatolicAverage + ", " : "") +
            (fieldDiastolicMax != null ? "fieldDiastolicMax=" + fieldDiastolicMax + ", " : "") +
            (fieldDiastolicMin != null ? "fieldDiastolicMin=" + fieldDiastolicMin + ", " : "") +
            (bodyPosition != null ? "bodyPosition=" + bodyPosition + ", " : "") +
            (measurementLocation != null ? "measurementLocation=" + measurementLocation + ", " : "") +
            (startTime != null ? "startTime=" + startTime + ", " : "") +
            (endTime != null ? "endTime=" + endTime + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
