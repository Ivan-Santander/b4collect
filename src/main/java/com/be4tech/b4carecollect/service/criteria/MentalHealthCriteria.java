package com.be4tech.b4carecollect.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.be4tech.b4carecollect.domain.MentalHealth} entity. This class is used
 * in {@link com.be4tech.b4carecollect.web.rest.MentalHealthResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /mental-healths?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MentalHealthCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private StringFilter usuarioId;

    private StringFilter empresaId;

    private StringFilter emotionDescription;

    private FloatFilter emotionValue;

    private InstantFilter startDate;

    private InstantFilter endDate;

    private FloatFilter mentalHealthScore;

    private Boolean distinct;

    public MentalHealthCriteria() {}

    public MentalHealthCriteria(MentalHealthCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.emotionDescription = other.emotionDescription == null ? null : other.emotionDescription.copy();
        this.emotionValue = other.emotionValue == null ? null : other.emotionValue.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.mentalHealthScore = other.mentalHealthScore == null ? null : other.mentalHealthScore.copy();
        this.distinct = other.distinct;
    }

    @Override
    public MentalHealthCriteria copy() {
        return new MentalHealthCriteria(this);
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

    public StringFilter getEmotionDescription() {
        return emotionDescription;
    }

    public StringFilter emotionDescription() {
        if (emotionDescription == null) {
            emotionDescription = new StringFilter();
        }
        return emotionDescription;
    }

    public void setEmotionDescription(StringFilter emotionDescription) {
        this.emotionDescription = emotionDescription;
    }

    public FloatFilter getEmotionValue() {
        return emotionValue;
    }

    public FloatFilter emotionValue() {
        if (emotionValue == null) {
            emotionValue = new FloatFilter();
        }
        return emotionValue;
    }

    public void setEmotionValue(FloatFilter emotionValue) {
        this.emotionValue = emotionValue;
    }

    public InstantFilter getStartDate() {
        return startDate;
    }

    public InstantFilter startDate() {
        if (startDate == null) {
            startDate = new InstantFilter();
        }
        return startDate;
    }

    public void setStartDate(InstantFilter startDate) {
        this.startDate = startDate;
    }

    public InstantFilter getEndDate() {
        return endDate;
    }

    public InstantFilter endDate() {
        if (endDate == null) {
            endDate = new InstantFilter();
        }
        return endDate;
    }

    public void setEndDate(InstantFilter endDate) {
        this.endDate = endDate;
    }

    public FloatFilter getMentalHealthScore() {
        return mentalHealthScore;
    }

    public FloatFilter mentalHealthScore() {
        if (mentalHealthScore == null) {
            mentalHealthScore = new FloatFilter();
        }
        return mentalHealthScore;
    }

    public void setMentalHealthScore(FloatFilter mentalHealthScore) {
        this.mentalHealthScore = mentalHealthScore;
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
        final MentalHealthCriteria that = (MentalHealthCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(emotionDescription, that.emotionDescription) &&
            Objects.equals(emotionValue, that.emotionValue) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(mentalHealthScore, that.mentalHealthScore) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usuarioId, empresaId, emotionDescription, emotionValue, startDate, endDate, mentalHealthScore, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MentalHealthCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            (emotionDescription != null ? "emotionDescription=" + emotionDescription + ", " : "") +
            (emotionValue != null ? "emotionValue=" + emotionValue + ", " : "") +
            (startDate != null ? "startDate=" + startDate + ", " : "") +
            (endDate != null ? "endDate=" + endDate + ", " : "") +
            (mentalHealthScore != null ? "mentalHealthScore=" + mentalHealthScore + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
