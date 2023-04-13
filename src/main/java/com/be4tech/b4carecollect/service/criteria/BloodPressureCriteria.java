package com.be4tech.b4carecollect.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.be4tech.b4carecollect.domain.BloodPressure} entity. This class is used
 * in {@link com.be4tech.b4carecollect.web.rest.BloodPressureResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /blood-pressures?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BloodPressureCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private StringFilter usuarioId;

    private StringFilter empresaId;

    private FloatFilter fieldBloodPressureSystolic;

    private FloatFilter fieldBloodPressureDiastolic;

    private StringFilter fieldBodyPosition;

    private IntegerFilter fieldBloodPressureMeasureLocation;

    private InstantFilter endTime;

    private Boolean distinct;

    public BloodPressureCriteria() {}

    public BloodPressureCriteria(BloodPressureCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.fieldBloodPressureSystolic = other.fieldBloodPressureSystolic == null ? null : other.fieldBloodPressureSystolic.copy();
        this.fieldBloodPressureDiastolic = other.fieldBloodPressureDiastolic == null ? null : other.fieldBloodPressureDiastolic.copy();
        this.fieldBodyPosition = other.fieldBodyPosition == null ? null : other.fieldBodyPosition.copy();
        this.fieldBloodPressureMeasureLocation =
            other.fieldBloodPressureMeasureLocation == null ? null : other.fieldBloodPressureMeasureLocation.copy();
        this.endTime = other.endTime == null ? null : other.endTime.copy();
        this.distinct = other.distinct;
    }

    @Override
    public BloodPressureCriteria copy() {
        return new BloodPressureCriteria(this);
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

    public FloatFilter getFieldBloodPressureSystolic() {
        return fieldBloodPressureSystolic;
    }

    public FloatFilter fieldBloodPressureSystolic() {
        if (fieldBloodPressureSystolic == null) {
            fieldBloodPressureSystolic = new FloatFilter();
        }
        return fieldBloodPressureSystolic;
    }

    public void setFieldBloodPressureSystolic(FloatFilter fieldBloodPressureSystolic) {
        this.fieldBloodPressureSystolic = fieldBloodPressureSystolic;
    }

    public FloatFilter getFieldBloodPressureDiastolic() {
        return fieldBloodPressureDiastolic;
    }

    public FloatFilter fieldBloodPressureDiastolic() {
        if (fieldBloodPressureDiastolic == null) {
            fieldBloodPressureDiastolic = new FloatFilter();
        }
        return fieldBloodPressureDiastolic;
    }

    public void setFieldBloodPressureDiastolic(FloatFilter fieldBloodPressureDiastolic) {
        this.fieldBloodPressureDiastolic = fieldBloodPressureDiastolic;
    }

    public StringFilter getFieldBodyPosition() {
        return fieldBodyPosition;
    }

    public StringFilter fieldBodyPosition() {
        if (fieldBodyPosition == null) {
            fieldBodyPosition = new StringFilter();
        }
        return fieldBodyPosition;
    }

    public void setFieldBodyPosition(StringFilter fieldBodyPosition) {
        this.fieldBodyPosition = fieldBodyPosition;
    }

    public IntegerFilter getFieldBloodPressureMeasureLocation() {
        return fieldBloodPressureMeasureLocation;
    }

    public IntegerFilter fieldBloodPressureMeasureLocation() {
        if (fieldBloodPressureMeasureLocation == null) {
            fieldBloodPressureMeasureLocation = new IntegerFilter();
        }
        return fieldBloodPressureMeasureLocation;
    }

    public void setFieldBloodPressureMeasureLocation(IntegerFilter fieldBloodPressureMeasureLocation) {
        this.fieldBloodPressureMeasureLocation = fieldBloodPressureMeasureLocation;
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
        final BloodPressureCriteria that = (BloodPressureCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(fieldBloodPressureSystolic, that.fieldBloodPressureSystolic) &&
            Objects.equals(fieldBloodPressureDiastolic, that.fieldBloodPressureDiastolic) &&
            Objects.equals(fieldBodyPosition, that.fieldBodyPosition) &&
            Objects.equals(fieldBloodPressureMeasureLocation, that.fieldBloodPressureMeasureLocation) &&
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
            fieldBloodPressureSystolic,
            fieldBloodPressureDiastolic,
            fieldBodyPosition,
            fieldBloodPressureMeasureLocation,
            endTime,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BloodPressureCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            (fieldBloodPressureSystolic != null ? "fieldBloodPressureSystolic=" + fieldBloodPressureSystolic + ", " : "") +
            (fieldBloodPressureDiastolic != null ? "fieldBloodPressureDiastolic=" + fieldBloodPressureDiastolic + ", " : "") +
            (fieldBodyPosition != null ? "fieldBodyPosition=" + fieldBodyPosition + ", " : "") +
            (fieldBloodPressureMeasureLocation != null ? "fieldBloodPressureMeasureLocation=" + fieldBloodPressureMeasureLocation + ", " : "") +
            (endTime != null ? "endTime=" + endTime + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
