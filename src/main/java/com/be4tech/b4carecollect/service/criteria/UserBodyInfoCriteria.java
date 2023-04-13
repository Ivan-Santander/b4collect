package com.be4tech.b4carecollect.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.be4tech.b4carecollect.domain.UserBodyInfo} entity. This class is used
 * in {@link com.be4tech.b4carecollect.web.rest.UserBodyInfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /user-body-infos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserBodyInfoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private StringFilter usuarioId;

    private StringFilter empresaId;

    private FloatFilter waistCircumference;

    private FloatFilter hipCircumference;

    private FloatFilter chestCircumference;

    private FloatFilter boneCompositionPercentaje;

    private FloatFilter muscleCompositionPercentaje;

    private BooleanFilter smoker;

    private FloatFilter waightKg;

    private FloatFilter heightCm;

    private FloatFilter bodyHealthScore;

    private IntegerFilter cardiovascularRisk;

    private Boolean distinct;

    public UserBodyInfoCriteria() {}

    public UserBodyInfoCriteria(UserBodyInfoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.waistCircumference = other.waistCircumference == null ? null : other.waistCircumference.copy();
        this.hipCircumference = other.hipCircumference == null ? null : other.hipCircumference.copy();
        this.chestCircumference = other.chestCircumference == null ? null : other.chestCircumference.copy();
        this.boneCompositionPercentaje = other.boneCompositionPercentaje == null ? null : other.boneCompositionPercentaje.copy();
        this.muscleCompositionPercentaje = other.muscleCompositionPercentaje == null ? null : other.muscleCompositionPercentaje.copy();
        this.smoker = other.smoker == null ? null : other.smoker.copy();
        this.waightKg = other.waightKg == null ? null : other.waightKg.copy();
        this.heightCm = other.heightCm == null ? null : other.heightCm.copy();
        this.bodyHealthScore = other.bodyHealthScore == null ? null : other.bodyHealthScore.copy();
        this.cardiovascularRisk = other.cardiovascularRisk == null ? null : other.cardiovascularRisk.copy();
        this.distinct = other.distinct;
    }

    @Override
    public UserBodyInfoCriteria copy() {
        return new UserBodyInfoCriteria(this);
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

    public FloatFilter getWaistCircumference() {
        return waistCircumference;
    }

    public FloatFilter waistCircumference() {
        if (waistCircumference == null) {
            waistCircumference = new FloatFilter();
        }
        return waistCircumference;
    }

    public void setWaistCircumference(FloatFilter waistCircumference) {
        this.waistCircumference = waistCircumference;
    }

    public FloatFilter getHipCircumference() {
        return hipCircumference;
    }

    public FloatFilter hipCircumference() {
        if (hipCircumference == null) {
            hipCircumference = new FloatFilter();
        }
        return hipCircumference;
    }

    public void setHipCircumference(FloatFilter hipCircumference) {
        this.hipCircumference = hipCircumference;
    }

    public FloatFilter getChestCircumference() {
        return chestCircumference;
    }

    public FloatFilter chestCircumference() {
        if (chestCircumference == null) {
            chestCircumference = new FloatFilter();
        }
        return chestCircumference;
    }

    public void setChestCircumference(FloatFilter chestCircumference) {
        this.chestCircumference = chestCircumference;
    }

    public FloatFilter getBoneCompositionPercentaje() {
        return boneCompositionPercentaje;
    }

    public FloatFilter boneCompositionPercentaje() {
        if (boneCompositionPercentaje == null) {
            boneCompositionPercentaje = new FloatFilter();
        }
        return boneCompositionPercentaje;
    }

    public void setBoneCompositionPercentaje(FloatFilter boneCompositionPercentaje) {
        this.boneCompositionPercentaje = boneCompositionPercentaje;
    }

    public FloatFilter getMuscleCompositionPercentaje() {
        return muscleCompositionPercentaje;
    }

    public FloatFilter muscleCompositionPercentaje() {
        if (muscleCompositionPercentaje == null) {
            muscleCompositionPercentaje = new FloatFilter();
        }
        return muscleCompositionPercentaje;
    }

    public void setMuscleCompositionPercentaje(FloatFilter muscleCompositionPercentaje) {
        this.muscleCompositionPercentaje = muscleCompositionPercentaje;
    }

    public BooleanFilter getSmoker() {
        return smoker;
    }

    public BooleanFilter smoker() {
        if (smoker == null) {
            smoker = new BooleanFilter();
        }
        return smoker;
    }

    public void setSmoker(BooleanFilter smoker) {
        this.smoker = smoker;
    }

    public FloatFilter getWaightKg() {
        return waightKg;
    }

    public FloatFilter waightKg() {
        if (waightKg == null) {
            waightKg = new FloatFilter();
        }
        return waightKg;
    }

    public void setWaightKg(FloatFilter waightKg) {
        this.waightKg = waightKg;
    }

    public FloatFilter getHeightCm() {
        return heightCm;
    }

    public FloatFilter heightCm() {
        if (heightCm == null) {
            heightCm = new FloatFilter();
        }
        return heightCm;
    }

    public void setHeightCm(FloatFilter heightCm) {
        this.heightCm = heightCm;
    }

    public FloatFilter getBodyHealthScore() {
        return bodyHealthScore;
    }

    public FloatFilter bodyHealthScore() {
        if (bodyHealthScore == null) {
            bodyHealthScore = new FloatFilter();
        }
        return bodyHealthScore;
    }

    public void setBodyHealthScore(FloatFilter bodyHealthScore) {
        this.bodyHealthScore = bodyHealthScore;
    }

    public IntegerFilter getCardiovascularRisk() {
        return cardiovascularRisk;
    }

    public IntegerFilter cardiovascularRisk() {
        if (cardiovascularRisk == null) {
            cardiovascularRisk = new IntegerFilter();
        }
        return cardiovascularRisk;
    }

    public void setCardiovascularRisk(IntegerFilter cardiovascularRisk) {
        this.cardiovascularRisk = cardiovascularRisk;
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
        final UserBodyInfoCriteria that = (UserBodyInfoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(waistCircumference, that.waistCircumference) &&
            Objects.equals(hipCircumference, that.hipCircumference) &&
            Objects.equals(chestCircumference, that.chestCircumference) &&
            Objects.equals(boneCompositionPercentaje, that.boneCompositionPercentaje) &&
            Objects.equals(muscleCompositionPercentaje, that.muscleCompositionPercentaje) &&
            Objects.equals(smoker, that.smoker) &&
            Objects.equals(waightKg, that.waightKg) &&
            Objects.equals(heightCm, that.heightCm) &&
            Objects.equals(bodyHealthScore, that.bodyHealthScore) &&
            Objects.equals(cardiovascularRisk, that.cardiovascularRisk) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            usuarioId,
            empresaId,
            waistCircumference,
            hipCircumference,
            chestCircumference,
            boneCompositionPercentaje,
            muscleCompositionPercentaje,
            smoker,
            waightKg,
            heightCm,
            bodyHealthScore,
            cardiovascularRisk,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserBodyInfoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            (waistCircumference != null ? "waistCircumference=" + waistCircumference + ", " : "") +
            (hipCircumference != null ? "hipCircumference=" + hipCircumference + ", " : "") +
            (chestCircumference != null ? "chestCircumference=" + chestCircumference + ", " : "") +
            (boneCompositionPercentaje != null ? "boneCompositionPercentaje=" + boneCompositionPercentaje + ", " : "") +
            (muscleCompositionPercentaje != null ? "muscleCompositionPercentaje=" + muscleCompositionPercentaje + ", " : "") +
            (smoker != null ? "smoker=" + smoker + ", " : "") +
            (waightKg != null ? "waightKg=" + waightKg + ", " : "") +
            (heightCm != null ? "heightCm=" + heightCm + ", " : "") +
            (bodyHealthScore != null ? "bodyHealthScore=" + bodyHealthScore + ", " : "") +
            (cardiovascularRisk != null ? "cardiovascularRisk=" + cardiovascularRisk + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
