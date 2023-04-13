package com.be4tech.b4carecollect.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.be4tech.b4carecollect.domain.ActivitySummary} entity. This class is used
 * in {@link com.be4tech.b4carecollect.web.rest.ActivitySummaryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /activity-summaries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ActivitySummaryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private StringFilter usuarioId;

    private StringFilter empresaId;

    private IntegerFilter fieldActivity;

    private IntegerFilter fieldDuration;

    private IntegerFilter fieldNumSegments;

    private InstantFilter startTime;

    private InstantFilter endTime;

    private Boolean distinct;

    public ActivitySummaryCriteria() {}

    public ActivitySummaryCriteria(ActivitySummaryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.fieldActivity = other.fieldActivity == null ? null : other.fieldActivity.copy();
        this.fieldDuration = other.fieldDuration == null ? null : other.fieldDuration.copy();
        this.fieldNumSegments = other.fieldNumSegments == null ? null : other.fieldNumSegments.copy();
        this.startTime = other.startTime == null ? null : other.startTime.copy();
        this.endTime = other.endTime == null ? null : other.endTime.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ActivitySummaryCriteria copy() {
        return new ActivitySummaryCriteria(this);
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

    public IntegerFilter getFieldActivity() {
        return fieldActivity;
    }

    public IntegerFilter fieldActivity() {
        if (fieldActivity == null) {
            fieldActivity = new IntegerFilter();
        }
        return fieldActivity;
    }

    public void setFieldActivity(IntegerFilter fieldActivity) {
        this.fieldActivity = fieldActivity;
    }

    public IntegerFilter getFieldDuration() {
        return fieldDuration;
    }

    public IntegerFilter fieldDuration() {
        if (fieldDuration == null) {
            fieldDuration = new IntegerFilter();
        }
        return fieldDuration;
    }

    public void setFieldDuration(IntegerFilter fieldDuration) {
        this.fieldDuration = fieldDuration;
    }

    public IntegerFilter getFieldNumSegments() {
        return fieldNumSegments;
    }

    public IntegerFilter fieldNumSegments() {
        if (fieldNumSegments == null) {
            fieldNumSegments = new IntegerFilter();
        }
        return fieldNumSegments;
    }

    public void setFieldNumSegments(IntegerFilter fieldNumSegments) {
        this.fieldNumSegments = fieldNumSegments;
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
        final ActivitySummaryCriteria that = (ActivitySummaryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(fieldActivity, that.fieldActivity) &&
            Objects.equals(fieldDuration, that.fieldDuration) &&
            Objects.equals(fieldNumSegments, that.fieldNumSegments) &&
            Objects.equals(startTime, that.startTime) &&
            Objects.equals(endTime, that.endTime) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usuarioId, empresaId, fieldActivity, fieldDuration, fieldNumSegments, startTime, endTime, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActivitySummaryCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            (fieldActivity != null ? "fieldActivity=" + fieldActivity + ", " : "") +
            (fieldDuration != null ? "fieldDuration=" + fieldDuration + ", " : "") +
            (fieldNumSegments != null ? "fieldNumSegments=" + fieldNumSegments + ", " : "") +
            (startTime != null ? "startTime=" + startTime + ", " : "") +
            (endTime != null ? "endTime=" + endTime + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
