package com.be4tech.b4carecollect.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.be4tech.b4carecollect.domain.UserMedicalInfo} entity. This class is used
 * in {@link com.be4tech.b4carecollect.web.rest.UserMedicalInfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /user-medical-infos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserMedicalInfoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private StringFilter usuarioId;

    private StringFilter empresaId;

    private BooleanFilter hypertension;

    private BooleanFilter highGlucose;

    private BooleanFilter hiabetes;

    private FloatFilter totalCholesterol;

    private FloatFilter hdlCholesterol;

    private StringFilter medicalMainCondition;

    private StringFilter medicalSecundaryCondition;

    private StringFilter medicalMainMedication;

    private StringFilter medicalSecundaryMedication;

    private IntegerFilter medicalScore;

    private InstantFilter endTime;

    private Boolean distinct;

    public UserMedicalInfoCriteria() {}

    public UserMedicalInfoCriteria(UserMedicalInfoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.hypertension = other.hypertension == null ? null : other.hypertension.copy();
        this.highGlucose = other.highGlucose == null ? null : other.highGlucose.copy();
        this.hiabetes = other.hiabetes == null ? null : other.hiabetes.copy();
        this.totalCholesterol = other.totalCholesterol == null ? null : other.totalCholesterol.copy();
        this.hdlCholesterol = other.hdlCholesterol == null ? null : other.hdlCholesterol.copy();
        this.medicalMainCondition = other.medicalMainCondition == null ? null : other.medicalMainCondition.copy();
        this.medicalSecundaryCondition = other.medicalSecundaryCondition == null ? null : other.medicalSecundaryCondition.copy();
        this.medicalMainMedication = other.medicalMainMedication == null ? null : other.medicalMainMedication.copy();
        this.medicalSecundaryMedication = other.medicalSecundaryMedication == null ? null : other.medicalSecundaryMedication.copy();
        this.medicalScore = other.medicalScore == null ? null : other.medicalScore.copy();
        this.endTime = other.endTime == null ? null : other.endTime.copy();
        this.distinct = other.distinct;
    }

    @Override
    public UserMedicalInfoCriteria copy() {
        return new UserMedicalInfoCriteria(this);
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

    public BooleanFilter getHypertension() {
        return hypertension;
    }

    public BooleanFilter hypertension() {
        if (hypertension == null) {
            hypertension = new BooleanFilter();
        }
        return hypertension;
    }

    public void setHypertension(BooleanFilter hypertension) {
        this.hypertension = hypertension;
    }

    public BooleanFilter getHighGlucose() {
        return highGlucose;
    }

    public BooleanFilter highGlucose() {
        if (highGlucose == null) {
            highGlucose = new BooleanFilter();
        }
        return highGlucose;
    }

    public void setHighGlucose(BooleanFilter highGlucose) {
        this.highGlucose = highGlucose;
    }

    public BooleanFilter getHiabetes() {
        return hiabetes;
    }

    public BooleanFilter hiabetes() {
        if (hiabetes == null) {
            hiabetes = new BooleanFilter();
        }
        return hiabetes;
    }

    public void setHiabetes(BooleanFilter hiabetes) {
        this.hiabetes = hiabetes;
    }

    public FloatFilter getTotalCholesterol() {
        return totalCholesterol;
    }

    public FloatFilter totalCholesterol() {
        if (totalCholesterol == null) {
            totalCholesterol = new FloatFilter();
        }
        return totalCholesterol;
    }

    public void setTotalCholesterol(FloatFilter totalCholesterol) {
        this.totalCholesterol = totalCholesterol;
    }

    public FloatFilter getHdlCholesterol() {
        return hdlCholesterol;
    }

    public FloatFilter hdlCholesterol() {
        if (hdlCholesterol == null) {
            hdlCholesterol = new FloatFilter();
        }
        return hdlCholesterol;
    }

    public void setHdlCholesterol(FloatFilter hdlCholesterol) {
        this.hdlCholesterol = hdlCholesterol;
    }

    public StringFilter getMedicalMainCondition() {
        return medicalMainCondition;
    }

    public StringFilter medicalMainCondition() {
        if (medicalMainCondition == null) {
            medicalMainCondition = new StringFilter();
        }
        return medicalMainCondition;
    }

    public void setMedicalMainCondition(StringFilter medicalMainCondition) {
        this.medicalMainCondition = medicalMainCondition;
    }

    public StringFilter getMedicalSecundaryCondition() {
        return medicalSecundaryCondition;
    }

    public StringFilter medicalSecundaryCondition() {
        if (medicalSecundaryCondition == null) {
            medicalSecundaryCondition = new StringFilter();
        }
        return medicalSecundaryCondition;
    }

    public void setMedicalSecundaryCondition(StringFilter medicalSecundaryCondition) {
        this.medicalSecundaryCondition = medicalSecundaryCondition;
    }

    public StringFilter getMedicalMainMedication() {
        return medicalMainMedication;
    }

    public StringFilter medicalMainMedication() {
        if (medicalMainMedication == null) {
            medicalMainMedication = new StringFilter();
        }
        return medicalMainMedication;
    }

    public void setMedicalMainMedication(StringFilter medicalMainMedication) {
        this.medicalMainMedication = medicalMainMedication;
    }

    public StringFilter getMedicalSecundaryMedication() {
        return medicalSecundaryMedication;
    }

    public StringFilter medicalSecundaryMedication() {
        if (medicalSecundaryMedication == null) {
            medicalSecundaryMedication = new StringFilter();
        }
        return medicalSecundaryMedication;
    }

    public void setMedicalSecundaryMedication(StringFilter medicalSecundaryMedication) {
        this.medicalSecundaryMedication = medicalSecundaryMedication;
    }

    public IntegerFilter getMedicalScore() {
        return medicalScore;
    }

    public IntegerFilter medicalScore() {
        if (medicalScore == null) {
            medicalScore = new IntegerFilter();
        }
        return medicalScore;
    }

    public void setMedicalScore(IntegerFilter medicalScore) {
        this.medicalScore = medicalScore;
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
        final UserMedicalInfoCriteria that = (UserMedicalInfoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(hypertension, that.hypertension) &&
            Objects.equals(highGlucose, that.highGlucose) &&
            Objects.equals(hiabetes, that.hiabetes) &&
            Objects.equals(totalCholesterol, that.totalCholesterol) &&
            Objects.equals(hdlCholesterol, that.hdlCholesterol) &&
            Objects.equals(medicalMainCondition, that.medicalMainCondition) &&
            Objects.equals(medicalSecundaryCondition, that.medicalSecundaryCondition) &&
            Objects.equals(medicalMainMedication, that.medicalMainMedication) &&
            Objects.equals(medicalSecundaryMedication, that.medicalSecundaryMedication) &&
            Objects.equals(medicalScore, that.medicalScore) &&
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
            hypertension,
            highGlucose,
            hiabetes,
            totalCholesterol,
            hdlCholesterol,
            medicalMainCondition,
            medicalSecundaryCondition,
            medicalMainMedication,
            medicalSecundaryMedication,
            medicalScore,
            endTime,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserMedicalInfoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            (hypertension != null ? "hypertension=" + hypertension + ", " : "") +
            (highGlucose != null ? "highGlucose=" + highGlucose + ", " : "") +
            (hiabetes != null ? "hiabetes=" + hiabetes + ", " : "") +
            (totalCholesterol != null ? "totalCholesterol=" + totalCholesterol + ", " : "") +
            (hdlCholesterol != null ? "hdlCholesterol=" + hdlCholesterol + ", " : "") +
            (medicalMainCondition != null ? "medicalMainCondition=" + medicalMainCondition + ", " : "") +
            (medicalSecundaryCondition != null ? "medicalSecundaryCondition=" + medicalSecundaryCondition + ", " : "") +
            (medicalMainMedication != null ? "medicalMainMedication=" + medicalMainMedication + ", " : "") +
            (medicalSecundaryMedication != null ? "medicalSecundaryMedication=" + medicalSecundaryMedication + ", " : "") +
            (medicalScore != null ? "medicalScore=" + medicalScore + ", " : "") +
            (endTime != null ? "endTime=" + endTime + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
