package com.be4tech.b4carecollect.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.be4tech.b4carecollect.domain.OxygenSaturationSummary} entity. This class is used
 * in {@link com.be4tech.b4carecollect.web.rest.OxygenSaturationSummaryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /oxygen-saturation-summaries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OxygenSaturationSummaryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private StringFilter usuarioId;

    private StringFilter empresaId;

    private FloatFilter fieldOxigenSaturationAverage;

    private FloatFilter fieldOxigenSaturationMax;

    private FloatFilter fieldOxigenSaturationMin;

    private FloatFilter fieldSuplementalOxigenFlowRateAverage;

    private FloatFilter fieldSuplementalOxigenFlowRateMax;

    private FloatFilter fieldSuplementalOxigenFlowRateMin;

    private IntegerFilter fieldOxigenTherapyAdministrationMode;

    private IntegerFilter fieldOxigenSaturationMode;

    private IntegerFilter fieldOxigenSaturationMeasurementMethod;

    private InstantFilter endTime;

    private Boolean distinct;

    public OxygenSaturationSummaryCriteria() {}

    public OxygenSaturationSummaryCriteria(OxygenSaturationSummaryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.fieldOxigenSaturationAverage = other.fieldOxigenSaturationAverage == null ? null : other.fieldOxigenSaturationAverage.copy();
        this.fieldOxigenSaturationMax = other.fieldOxigenSaturationMax == null ? null : other.fieldOxigenSaturationMax.copy();
        this.fieldOxigenSaturationMin = other.fieldOxigenSaturationMin == null ? null : other.fieldOxigenSaturationMin.copy();
        this.fieldSuplementalOxigenFlowRateAverage =
            other.fieldSuplementalOxigenFlowRateAverage == null ? null : other.fieldSuplementalOxigenFlowRateAverage.copy();
        this.fieldSuplementalOxigenFlowRateMax =
            other.fieldSuplementalOxigenFlowRateMax == null ? null : other.fieldSuplementalOxigenFlowRateMax.copy();
        this.fieldSuplementalOxigenFlowRateMin =
            other.fieldSuplementalOxigenFlowRateMin == null ? null : other.fieldSuplementalOxigenFlowRateMin.copy();
        this.fieldOxigenTherapyAdministrationMode =
            other.fieldOxigenTherapyAdministrationMode == null ? null : other.fieldOxigenTherapyAdministrationMode.copy();
        this.fieldOxigenSaturationMode = other.fieldOxigenSaturationMode == null ? null : other.fieldOxigenSaturationMode.copy();
        this.fieldOxigenSaturationMeasurementMethod =
            other.fieldOxigenSaturationMeasurementMethod == null ? null : other.fieldOxigenSaturationMeasurementMethod.copy();
        this.endTime = other.endTime == null ? null : other.endTime.copy();
        this.distinct = other.distinct;
    }

    @Override
    public OxygenSaturationSummaryCriteria copy() {
        return new OxygenSaturationSummaryCriteria(this);
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

    public FloatFilter getFieldOxigenSaturationAverage() {
        return fieldOxigenSaturationAverage;
    }

    public FloatFilter fieldOxigenSaturationAverage() {
        if (fieldOxigenSaturationAverage == null) {
            fieldOxigenSaturationAverage = new FloatFilter();
        }
        return fieldOxigenSaturationAverage;
    }

    public void setFieldOxigenSaturationAverage(FloatFilter fieldOxigenSaturationAverage) {
        this.fieldOxigenSaturationAverage = fieldOxigenSaturationAverage;
    }

    public FloatFilter getFieldOxigenSaturationMax() {
        return fieldOxigenSaturationMax;
    }

    public FloatFilter fieldOxigenSaturationMax() {
        if (fieldOxigenSaturationMax == null) {
            fieldOxigenSaturationMax = new FloatFilter();
        }
        return fieldOxigenSaturationMax;
    }

    public void setFieldOxigenSaturationMax(FloatFilter fieldOxigenSaturationMax) {
        this.fieldOxigenSaturationMax = fieldOxigenSaturationMax;
    }

    public FloatFilter getFieldOxigenSaturationMin() {
        return fieldOxigenSaturationMin;
    }

    public FloatFilter fieldOxigenSaturationMin() {
        if (fieldOxigenSaturationMin == null) {
            fieldOxigenSaturationMin = new FloatFilter();
        }
        return fieldOxigenSaturationMin;
    }

    public void setFieldOxigenSaturationMin(FloatFilter fieldOxigenSaturationMin) {
        this.fieldOxigenSaturationMin = fieldOxigenSaturationMin;
    }

    public FloatFilter getFieldSuplementalOxigenFlowRateAverage() {
        return fieldSuplementalOxigenFlowRateAverage;
    }

    public FloatFilter fieldSuplementalOxigenFlowRateAverage() {
        if (fieldSuplementalOxigenFlowRateAverage == null) {
            fieldSuplementalOxigenFlowRateAverage = new FloatFilter();
        }
        return fieldSuplementalOxigenFlowRateAverage;
    }

    public void setFieldSuplementalOxigenFlowRateAverage(FloatFilter fieldSuplementalOxigenFlowRateAverage) {
        this.fieldSuplementalOxigenFlowRateAverage = fieldSuplementalOxigenFlowRateAverage;
    }

    public FloatFilter getFieldSuplementalOxigenFlowRateMax() {
        return fieldSuplementalOxigenFlowRateMax;
    }

    public FloatFilter fieldSuplementalOxigenFlowRateMax() {
        if (fieldSuplementalOxigenFlowRateMax == null) {
            fieldSuplementalOxigenFlowRateMax = new FloatFilter();
        }
        return fieldSuplementalOxigenFlowRateMax;
    }

    public void setFieldSuplementalOxigenFlowRateMax(FloatFilter fieldSuplementalOxigenFlowRateMax) {
        this.fieldSuplementalOxigenFlowRateMax = fieldSuplementalOxigenFlowRateMax;
    }

    public FloatFilter getFieldSuplementalOxigenFlowRateMin() {
        return fieldSuplementalOxigenFlowRateMin;
    }

    public FloatFilter fieldSuplementalOxigenFlowRateMin() {
        if (fieldSuplementalOxigenFlowRateMin == null) {
            fieldSuplementalOxigenFlowRateMin = new FloatFilter();
        }
        return fieldSuplementalOxigenFlowRateMin;
    }

    public void setFieldSuplementalOxigenFlowRateMin(FloatFilter fieldSuplementalOxigenFlowRateMin) {
        this.fieldSuplementalOxigenFlowRateMin = fieldSuplementalOxigenFlowRateMin;
    }

    public IntegerFilter getFieldOxigenTherapyAdministrationMode() {
        return fieldOxigenTherapyAdministrationMode;
    }

    public IntegerFilter fieldOxigenTherapyAdministrationMode() {
        if (fieldOxigenTherapyAdministrationMode == null) {
            fieldOxigenTherapyAdministrationMode = new IntegerFilter();
        }
        return fieldOxigenTherapyAdministrationMode;
    }

    public void setFieldOxigenTherapyAdministrationMode(IntegerFilter fieldOxigenTherapyAdministrationMode) {
        this.fieldOxigenTherapyAdministrationMode = fieldOxigenTherapyAdministrationMode;
    }

    public IntegerFilter getFieldOxigenSaturationMode() {
        return fieldOxigenSaturationMode;
    }

    public IntegerFilter fieldOxigenSaturationMode() {
        if (fieldOxigenSaturationMode == null) {
            fieldOxigenSaturationMode = new IntegerFilter();
        }
        return fieldOxigenSaturationMode;
    }

    public void setFieldOxigenSaturationMode(IntegerFilter fieldOxigenSaturationMode) {
        this.fieldOxigenSaturationMode = fieldOxigenSaturationMode;
    }

    public IntegerFilter getFieldOxigenSaturationMeasurementMethod() {
        return fieldOxigenSaturationMeasurementMethod;
    }

    public IntegerFilter fieldOxigenSaturationMeasurementMethod() {
        if (fieldOxigenSaturationMeasurementMethod == null) {
            fieldOxigenSaturationMeasurementMethod = new IntegerFilter();
        }
        return fieldOxigenSaturationMeasurementMethod;
    }

    public void setFieldOxigenSaturationMeasurementMethod(IntegerFilter fieldOxigenSaturationMeasurementMethod) {
        this.fieldOxigenSaturationMeasurementMethod = fieldOxigenSaturationMeasurementMethod;
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
        final OxygenSaturationSummaryCriteria that = (OxygenSaturationSummaryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(fieldOxigenSaturationAverage, that.fieldOxigenSaturationAverage) &&
            Objects.equals(fieldOxigenSaturationMax, that.fieldOxigenSaturationMax) &&
            Objects.equals(fieldOxigenSaturationMin, that.fieldOxigenSaturationMin) &&
            Objects.equals(fieldSuplementalOxigenFlowRateAverage, that.fieldSuplementalOxigenFlowRateAverage) &&
            Objects.equals(fieldSuplementalOxigenFlowRateMax, that.fieldSuplementalOxigenFlowRateMax) &&
            Objects.equals(fieldSuplementalOxigenFlowRateMin, that.fieldSuplementalOxigenFlowRateMin) &&
            Objects.equals(fieldOxigenTherapyAdministrationMode, that.fieldOxigenTherapyAdministrationMode) &&
            Objects.equals(fieldOxigenSaturationMode, that.fieldOxigenSaturationMode) &&
            Objects.equals(fieldOxigenSaturationMeasurementMethod, that.fieldOxigenSaturationMeasurementMethod) &&
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
            fieldOxigenSaturationAverage,
            fieldOxigenSaturationMax,
            fieldOxigenSaturationMin,
            fieldSuplementalOxigenFlowRateAverage,
            fieldSuplementalOxigenFlowRateMax,
            fieldSuplementalOxigenFlowRateMin,
            fieldOxigenTherapyAdministrationMode,
            fieldOxigenSaturationMode,
            fieldOxigenSaturationMeasurementMethod,
            endTime,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OxygenSaturationSummaryCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            (fieldOxigenSaturationAverage != null ? "fieldOxigenSaturationAverage=" + fieldOxigenSaturationAverage + ", " : "") +
            (fieldOxigenSaturationMax != null ? "fieldOxigenSaturationMax=" + fieldOxigenSaturationMax + ", " : "") +
            (fieldOxigenSaturationMin != null ? "fieldOxigenSaturationMin=" + fieldOxigenSaturationMin + ", " : "") +
            (fieldSuplementalOxigenFlowRateAverage != null ? "fieldSuplementalOxigenFlowRateAverage=" + fieldSuplementalOxigenFlowRateAverage + ", " : "") +
            (fieldSuplementalOxigenFlowRateMax != null ? "fieldSuplementalOxigenFlowRateMax=" + fieldSuplementalOxigenFlowRateMax + ", " : "") +
            (fieldSuplementalOxigenFlowRateMin != null ? "fieldSuplementalOxigenFlowRateMin=" + fieldSuplementalOxigenFlowRateMin + ", " : "") +
            (fieldOxigenTherapyAdministrationMode != null ? "fieldOxigenTherapyAdministrationMode=" + fieldOxigenTherapyAdministrationMode + ", " : "") +
            (fieldOxigenSaturationMode != null ? "fieldOxigenSaturationMode=" + fieldOxigenSaturationMode + ", " : "") +
            (fieldOxigenSaturationMeasurementMethod != null ? "fieldOxigenSaturationMeasurementMethod=" + fieldOxigenSaturationMeasurementMethod + ", " : "") +
            (endTime != null ? "endTime=" + endTime + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
