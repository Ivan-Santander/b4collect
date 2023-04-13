package com.be4tech.b4carecollect.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.be4tech.b4carecollect.domain.BloodGlucose} entity. This class is used
 * in {@link com.be4tech.b4carecollect.web.rest.BloodGlucoseResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /blood-glucoses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BloodGlucoseCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private StringFilter usuarioId;

    private StringFilter empresaId;

    private FloatFilter fieldBloodGlucoseLevel;

    private IntegerFilter fieldTemporalRelationToMeal;

    private IntegerFilter fieldMealType;

    private IntegerFilter fieldTemporalRelationToSleep;

    private IntegerFilter fieldBloodGlucoseSpecimenSource;

    private InstantFilter endTime;

    private Boolean distinct;

    public BloodGlucoseCriteria() {}

    public BloodGlucoseCriteria(BloodGlucoseCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.fieldBloodGlucoseLevel = other.fieldBloodGlucoseLevel == null ? null : other.fieldBloodGlucoseLevel.copy();
        this.fieldTemporalRelationToMeal = other.fieldTemporalRelationToMeal == null ? null : other.fieldTemporalRelationToMeal.copy();
        this.fieldMealType = other.fieldMealType == null ? null : other.fieldMealType.copy();
        this.fieldTemporalRelationToSleep = other.fieldTemporalRelationToSleep == null ? null : other.fieldTemporalRelationToSleep.copy();
        this.fieldBloodGlucoseSpecimenSource =
            other.fieldBloodGlucoseSpecimenSource == null ? null : other.fieldBloodGlucoseSpecimenSource.copy();
        this.endTime = other.endTime == null ? null : other.endTime.copy();
        this.distinct = other.distinct;
    }

    @Override
    public BloodGlucoseCriteria copy() {
        return new BloodGlucoseCriteria(this);
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

    public FloatFilter getFieldBloodGlucoseLevel() {
        return fieldBloodGlucoseLevel;
    }

    public FloatFilter fieldBloodGlucoseLevel() {
        if (fieldBloodGlucoseLevel == null) {
            fieldBloodGlucoseLevel = new FloatFilter();
        }
        return fieldBloodGlucoseLevel;
    }

    public void setFieldBloodGlucoseLevel(FloatFilter fieldBloodGlucoseLevel) {
        this.fieldBloodGlucoseLevel = fieldBloodGlucoseLevel;
    }

    public IntegerFilter getFieldTemporalRelationToMeal() {
        return fieldTemporalRelationToMeal;
    }

    public IntegerFilter fieldTemporalRelationToMeal() {
        if (fieldTemporalRelationToMeal == null) {
            fieldTemporalRelationToMeal = new IntegerFilter();
        }
        return fieldTemporalRelationToMeal;
    }

    public void setFieldTemporalRelationToMeal(IntegerFilter fieldTemporalRelationToMeal) {
        this.fieldTemporalRelationToMeal = fieldTemporalRelationToMeal;
    }

    public IntegerFilter getFieldMealType() {
        return fieldMealType;
    }

    public IntegerFilter fieldMealType() {
        if (fieldMealType == null) {
            fieldMealType = new IntegerFilter();
        }
        return fieldMealType;
    }

    public void setFieldMealType(IntegerFilter fieldMealType) {
        this.fieldMealType = fieldMealType;
    }

    public IntegerFilter getFieldTemporalRelationToSleep() {
        return fieldTemporalRelationToSleep;
    }

    public IntegerFilter fieldTemporalRelationToSleep() {
        if (fieldTemporalRelationToSleep == null) {
            fieldTemporalRelationToSleep = new IntegerFilter();
        }
        return fieldTemporalRelationToSleep;
    }

    public void setFieldTemporalRelationToSleep(IntegerFilter fieldTemporalRelationToSleep) {
        this.fieldTemporalRelationToSleep = fieldTemporalRelationToSleep;
    }

    public IntegerFilter getFieldBloodGlucoseSpecimenSource() {
        return fieldBloodGlucoseSpecimenSource;
    }

    public IntegerFilter fieldBloodGlucoseSpecimenSource() {
        if (fieldBloodGlucoseSpecimenSource == null) {
            fieldBloodGlucoseSpecimenSource = new IntegerFilter();
        }
        return fieldBloodGlucoseSpecimenSource;
    }

    public void setFieldBloodGlucoseSpecimenSource(IntegerFilter fieldBloodGlucoseSpecimenSource) {
        this.fieldBloodGlucoseSpecimenSource = fieldBloodGlucoseSpecimenSource;
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
        final BloodGlucoseCriteria that = (BloodGlucoseCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(fieldBloodGlucoseLevel, that.fieldBloodGlucoseLevel) &&
            Objects.equals(fieldTemporalRelationToMeal, that.fieldTemporalRelationToMeal) &&
            Objects.equals(fieldMealType, that.fieldMealType) &&
            Objects.equals(fieldTemporalRelationToSleep, that.fieldTemporalRelationToSleep) &&
            Objects.equals(fieldBloodGlucoseSpecimenSource, that.fieldBloodGlucoseSpecimenSource) &&
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
            fieldBloodGlucoseLevel,
            fieldTemporalRelationToMeal,
            fieldMealType,
            fieldTemporalRelationToSleep,
            fieldBloodGlucoseSpecimenSource,
            endTime,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BloodGlucoseCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            (fieldBloodGlucoseLevel != null ? "fieldBloodGlucoseLevel=" + fieldBloodGlucoseLevel + ", " : "") +
            (fieldTemporalRelationToMeal != null ? "fieldTemporalRelationToMeal=" + fieldTemporalRelationToMeal + ", " : "") +
            (fieldMealType != null ? "fieldMealType=" + fieldMealType + ", " : "") +
            (fieldTemporalRelationToSleep != null ? "fieldTemporalRelationToSleep=" + fieldTemporalRelationToSleep + ", " : "") +
            (fieldBloodGlucoseSpecimenSource != null ? "fieldBloodGlucoseSpecimenSource=" + fieldBloodGlucoseSpecimenSource + ", " : "") +
            (endTime != null ? "endTime=" + endTime + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
