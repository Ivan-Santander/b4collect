package com.be4tech.b4carecollect.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.be4tech.b4carecollect.domain.BodyTemperature} entity. This class is used
 * in {@link com.be4tech.b4carecollect.web.rest.BodyTemperatureResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /body-temperatures?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BodyTemperatureCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private StringFilter usuarioId;

    private StringFilter empresaId;

    private FloatFilter fieldBodyTemperature;

    private IntegerFilter fieldBodyTemperatureMeasureLocation;

    private InstantFilter endTime;

    private Boolean distinct;

    public BodyTemperatureCriteria() {}

    public BodyTemperatureCriteria(BodyTemperatureCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.fieldBodyTemperature = other.fieldBodyTemperature == null ? null : other.fieldBodyTemperature.copy();
        this.fieldBodyTemperatureMeasureLocation =
            other.fieldBodyTemperatureMeasureLocation == null ? null : other.fieldBodyTemperatureMeasureLocation.copy();
        this.endTime = other.endTime == null ? null : other.endTime.copy();
        this.distinct = other.distinct;
    }

    @Override
    public BodyTemperatureCriteria copy() {
        return new BodyTemperatureCriteria(this);
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

    public FloatFilter getFieldBodyTemperature() {
        return fieldBodyTemperature;
    }

    public FloatFilter fieldBodyTemperature() {
        if (fieldBodyTemperature == null) {
            fieldBodyTemperature = new FloatFilter();
        }
        return fieldBodyTemperature;
    }

    public void setFieldBodyTemperature(FloatFilter fieldBodyTemperature) {
        this.fieldBodyTemperature = fieldBodyTemperature;
    }

    public IntegerFilter getFieldBodyTemperatureMeasureLocation() {
        return fieldBodyTemperatureMeasureLocation;
    }

    public IntegerFilter fieldBodyTemperatureMeasureLocation() {
        if (fieldBodyTemperatureMeasureLocation == null) {
            fieldBodyTemperatureMeasureLocation = new IntegerFilter();
        }
        return fieldBodyTemperatureMeasureLocation;
    }

    public void setFieldBodyTemperatureMeasureLocation(IntegerFilter fieldBodyTemperatureMeasureLocation) {
        this.fieldBodyTemperatureMeasureLocation = fieldBodyTemperatureMeasureLocation;
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
        final BodyTemperatureCriteria that = (BodyTemperatureCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(fieldBodyTemperature, that.fieldBodyTemperature) &&
            Objects.equals(fieldBodyTemperatureMeasureLocation, that.fieldBodyTemperatureMeasureLocation) &&
            Objects.equals(endTime, that.endTime) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usuarioId, empresaId, fieldBodyTemperature, fieldBodyTemperatureMeasureLocation, endTime, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BodyTemperatureCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            (fieldBodyTemperature != null ? "fieldBodyTemperature=" + fieldBodyTemperature + ", " : "") +
            (fieldBodyTemperatureMeasureLocation != null ? "fieldBodyTemperatureMeasureLocation=" + fieldBodyTemperatureMeasureLocation + ", " : "") +
            (endTime != null ? "endTime=" + endTime + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
