package com.be4tech.b4carecollect.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.be4tech.b4carecollect.domain.AlarmRiskIndexSummary} entity. This class is used
 * in {@link com.be4tech.b4carecollect.web.rest.AlarmRiskIndexSummaryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /alarm-risk-index-summaries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AlarmRiskIndexSummaryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private StringFilter usuarioId;

    private StringFilter empresaId;

    private FloatFilter fieldAlarmRiskAverage;

    private FloatFilter fieldAlarmRiskMax;

    private FloatFilter fieldAlarmRiskMin;

    private InstantFilter startTime;

    private InstantFilter endTime;

    private Boolean distinct;

    public AlarmRiskIndexSummaryCriteria() {}

    public AlarmRiskIndexSummaryCriteria(AlarmRiskIndexSummaryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.fieldAlarmRiskAverage = other.fieldAlarmRiskAverage == null ? null : other.fieldAlarmRiskAverage.copy();
        this.fieldAlarmRiskMax = other.fieldAlarmRiskMax == null ? null : other.fieldAlarmRiskMax.copy();
        this.fieldAlarmRiskMin = other.fieldAlarmRiskMin == null ? null : other.fieldAlarmRiskMin.copy();
        this.startTime = other.startTime == null ? null : other.startTime.copy();
        this.endTime = other.endTime == null ? null : other.endTime.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AlarmRiskIndexSummaryCriteria copy() {
        return new AlarmRiskIndexSummaryCriteria(this);
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

    public FloatFilter getFieldAlarmRiskAverage() {
        return fieldAlarmRiskAverage;
    }

    public FloatFilter fieldAlarmRiskAverage() {
        if (fieldAlarmRiskAverage == null) {
            fieldAlarmRiskAverage = new FloatFilter();
        }
        return fieldAlarmRiskAverage;
    }

    public void setFieldAlarmRiskAverage(FloatFilter fieldAlarmRiskAverage) {
        this.fieldAlarmRiskAverage = fieldAlarmRiskAverage;
    }

    public FloatFilter getFieldAlarmRiskMax() {
        return fieldAlarmRiskMax;
    }

    public FloatFilter fieldAlarmRiskMax() {
        if (fieldAlarmRiskMax == null) {
            fieldAlarmRiskMax = new FloatFilter();
        }
        return fieldAlarmRiskMax;
    }

    public void setFieldAlarmRiskMax(FloatFilter fieldAlarmRiskMax) {
        this.fieldAlarmRiskMax = fieldAlarmRiskMax;
    }

    public FloatFilter getFieldAlarmRiskMin() {
        return fieldAlarmRiskMin;
    }

    public FloatFilter fieldAlarmRiskMin() {
        if (fieldAlarmRiskMin == null) {
            fieldAlarmRiskMin = new FloatFilter();
        }
        return fieldAlarmRiskMin;
    }

    public void setFieldAlarmRiskMin(FloatFilter fieldAlarmRiskMin) {
        this.fieldAlarmRiskMin = fieldAlarmRiskMin;
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
        final AlarmRiskIndexSummaryCriteria that = (AlarmRiskIndexSummaryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(fieldAlarmRiskAverage, that.fieldAlarmRiskAverage) &&
            Objects.equals(fieldAlarmRiskMax, that.fieldAlarmRiskMax) &&
            Objects.equals(fieldAlarmRiskMin, that.fieldAlarmRiskMin) &&
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
            fieldAlarmRiskAverage,
            fieldAlarmRiskMax,
            fieldAlarmRiskMin,
            startTime,
            endTime,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlarmRiskIndexSummaryCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            (fieldAlarmRiskAverage != null ? "fieldAlarmRiskAverage=" + fieldAlarmRiskAverage + ", " : "") +
            (fieldAlarmRiskMax != null ? "fieldAlarmRiskMax=" + fieldAlarmRiskMax + ", " : "") +
            (fieldAlarmRiskMin != null ? "fieldAlarmRiskMin=" + fieldAlarmRiskMin + ", " : "") +
            (startTime != null ? "startTime=" + startTime + ", " : "") +
            (endTime != null ? "endTime=" + endTime + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
