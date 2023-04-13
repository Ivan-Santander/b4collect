package com.be4tech.b4carecollect.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.be4tech.b4carecollect.domain.OxygenSaturation} entity. This class is used
 * in {@link com.be4tech.b4carecollect.web.rest.OxygenSaturationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /oxygen-saturations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OxygenSaturationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private StringFilter usuarioId;

    private StringFilter empresaId;

    private FloatFilter fieldOxigenSaturation;

    private FloatFilter fieldSuplementalOxigenFlowRate;

    private IntegerFilter fieldOxigenTherapyAdministrationMode;

    private IntegerFilter fieldOxigenSaturationMode;

    private IntegerFilter fieldOxigenSaturationMeasurementMethod;

    private InstantFilter endTime;

    private Boolean distinct;

    public OxygenSaturationCriteria() {}

    public OxygenSaturationCriteria(OxygenSaturationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.fieldOxigenSaturation = other.fieldOxigenSaturation == null ? null : other.fieldOxigenSaturation.copy();
        this.fieldSuplementalOxigenFlowRate =
            other.fieldSuplementalOxigenFlowRate == null ? null : other.fieldSuplementalOxigenFlowRate.copy();
        this.fieldOxigenTherapyAdministrationMode =
            other.fieldOxigenTherapyAdministrationMode == null ? null : other.fieldOxigenTherapyAdministrationMode.copy();
        this.fieldOxigenSaturationMode = other.fieldOxigenSaturationMode == null ? null : other.fieldOxigenSaturationMode.copy();
        this.fieldOxigenSaturationMeasurementMethod =
            other.fieldOxigenSaturationMeasurementMethod == null ? null : other.fieldOxigenSaturationMeasurementMethod.copy();
        this.endTime = other.endTime == null ? null : other.endTime.copy();
        this.distinct = other.distinct;
    }

    @Override
    public OxygenSaturationCriteria copy() {
        return new OxygenSaturationCriteria(this);
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

    public FloatFilter getFieldOxigenSaturation() {
        return fieldOxigenSaturation;
    }

    public FloatFilter fieldOxigenSaturation() {
        if (fieldOxigenSaturation == null) {
            fieldOxigenSaturation = new FloatFilter();
        }
        return fieldOxigenSaturation;
    }

    public void setFieldOxigenSaturation(FloatFilter fieldOxigenSaturation) {
        this.fieldOxigenSaturation = fieldOxigenSaturation;
    }

    public FloatFilter getFieldSuplementalOxigenFlowRate() {
        return fieldSuplementalOxigenFlowRate;
    }

    public FloatFilter fieldSuplementalOxigenFlowRate() {
        if (fieldSuplementalOxigenFlowRate == null) {
            fieldSuplementalOxigenFlowRate = new FloatFilter();
        }
        return fieldSuplementalOxigenFlowRate;
    }

    public void setFieldSuplementalOxigenFlowRate(FloatFilter fieldSuplementalOxigenFlowRate) {
        this.fieldSuplementalOxigenFlowRate = fieldSuplementalOxigenFlowRate;
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
        final OxygenSaturationCriteria that = (OxygenSaturationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(fieldOxigenSaturation, that.fieldOxigenSaturation) &&
            Objects.equals(fieldSuplementalOxigenFlowRate, that.fieldSuplementalOxigenFlowRate) &&
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
            fieldOxigenSaturation,
            fieldSuplementalOxigenFlowRate,
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
        return "OxygenSaturationCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            (fieldOxigenSaturation != null ? "fieldOxigenSaturation=" + fieldOxigenSaturation + ", " : "") +
            (fieldSuplementalOxigenFlowRate != null ? "fieldSuplementalOxigenFlowRate=" + fieldSuplementalOxigenFlowRate + ", " : "") +
            (fieldOxigenTherapyAdministrationMode != null ? "fieldOxigenTherapyAdministrationMode=" + fieldOxigenTherapyAdministrationMode + ", " : "") +
            (fieldOxigenSaturationMode != null ? "fieldOxigenSaturationMode=" + fieldOxigenSaturationMode + ", " : "") +
            (fieldOxigenSaturationMeasurementMethod != null ? "fieldOxigenSaturationMeasurementMethod=" + fieldOxigenSaturationMeasurementMethod + ", " : "") +
            (endTime != null ? "endTime=" + endTime + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
