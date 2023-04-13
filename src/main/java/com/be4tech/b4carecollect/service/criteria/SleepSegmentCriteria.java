package com.be4tech.b4carecollect.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.be4tech.b4carecollect.domain.SleepSegment} entity. This class is used
 * in {@link com.be4tech.b4carecollect.web.rest.SleepSegmentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sleep-segments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SleepSegmentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private StringFilter usuarioId;

    private StringFilter empresaId;

    private IntegerFilter fieldSleepSegmentType;

    private IntegerFilter fieldBloodGlucoseSpecimenSource;

    private InstantFilter startTime;

    private InstantFilter endTime;

    private Boolean distinct;

    public SleepSegmentCriteria() {}

    public SleepSegmentCriteria(SleepSegmentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.fieldSleepSegmentType = other.fieldSleepSegmentType == null ? null : other.fieldSleepSegmentType.copy();
        this.fieldBloodGlucoseSpecimenSource =
            other.fieldBloodGlucoseSpecimenSource == null ? null : other.fieldBloodGlucoseSpecimenSource.copy();
        this.startTime = other.startTime == null ? null : other.startTime.copy();
        this.endTime = other.endTime == null ? null : other.endTime.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SleepSegmentCriteria copy() {
        return new SleepSegmentCriteria(this);
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

    public IntegerFilter getFieldSleepSegmentType() {
        return fieldSleepSegmentType;
    }

    public IntegerFilter fieldSleepSegmentType() {
        if (fieldSleepSegmentType == null) {
            fieldSleepSegmentType = new IntegerFilter();
        }
        return fieldSleepSegmentType;
    }

    public void setFieldSleepSegmentType(IntegerFilter fieldSleepSegmentType) {
        this.fieldSleepSegmentType = fieldSleepSegmentType;
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
        final SleepSegmentCriteria that = (SleepSegmentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(fieldSleepSegmentType, that.fieldSleepSegmentType) &&
            Objects.equals(fieldBloodGlucoseSpecimenSource, that.fieldBloodGlucoseSpecimenSource) &&
            Objects.equals(startTime, that.startTime) &&
            Objects.equals(endTime, that.endTime) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usuarioId, empresaId, fieldSleepSegmentType, fieldBloodGlucoseSpecimenSource, startTime, endTime, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SleepSegmentCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            (fieldSleepSegmentType != null ? "fieldSleepSegmentType=" + fieldSleepSegmentType + ", " : "") +
            (fieldBloodGlucoseSpecimenSource != null ? "fieldBloodGlucoseSpecimenSource=" + fieldBloodGlucoseSpecimenSource + ", " : "") +
            (startTime != null ? "startTime=" + startTime + ", " : "") +
            (endTime != null ? "endTime=" + endTime + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
