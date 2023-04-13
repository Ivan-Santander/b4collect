package com.be4tech.b4carecollect.domain;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UserBodyInfo.
 */
@Entity
@Table(name = "user_body_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserBodyInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "usuario_id")
    private String usuarioId;

    @Column(name = "empresa_id")
    private String empresaId;

    @Column(name = "waist_circumference")
    private Float waistCircumference;

    @Column(name = "hip_circumference")
    private Float hipCircumference;

    @Column(name = "chest_circumference")
    private Float chestCircumference;

    @Column(name = "bone_composition_percentaje")
    private Float boneCompositionPercentaje;

    @Column(name = "muscle_composition_percentaje")
    private Float muscleCompositionPercentaje;

    @Column(name = "smoker")
    private Boolean smoker;

    @Column(name = "waight_kg")
    private Float waightKg;

    @Column(name = "height_cm")
    private Float heightCm;

    @Column(name = "body_health_score")
    private Float bodyHealthScore;

    @Column(name = "cardiovascular_risk")
    private Integer cardiovascularRisk;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public UserBodyInfo id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return this.usuarioId;
    }

    public UserBodyInfo usuarioId(String usuarioId) {
        this.setUsuarioId(usuarioId);
        return this;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEmpresaId() {
        return this.empresaId;
    }

    public UserBodyInfo empresaId(String empresaId) {
        this.setEmpresaId(empresaId);
        return this;
    }

    public void setEmpresaId(String empresaId) {
        this.empresaId = empresaId;
    }

    public Float getWaistCircumference() {
        return this.waistCircumference;
    }

    public UserBodyInfo waistCircumference(Float waistCircumference) {
        this.setWaistCircumference(waistCircumference);
        return this;
    }

    public void setWaistCircumference(Float waistCircumference) {
        this.waistCircumference = waistCircumference;
    }

    public Float getHipCircumference() {
        return this.hipCircumference;
    }

    public UserBodyInfo hipCircumference(Float hipCircumference) {
        this.setHipCircumference(hipCircumference);
        return this;
    }

    public void setHipCircumference(Float hipCircumference) {
        this.hipCircumference = hipCircumference;
    }

    public Float getChestCircumference() {
        return this.chestCircumference;
    }

    public UserBodyInfo chestCircumference(Float chestCircumference) {
        this.setChestCircumference(chestCircumference);
        return this;
    }

    public void setChestCircumference(Float chestCircumference) {
        this.chestCircumference = chestCircumference;
    }

    public Float getBoneCompositionPercentaje() {
        return this.boneCompositionPercentaje;
    }

    public UserBodyInfo boneCompositionPercentaje(Float boneCompositionPercentaje) {
        this.setBoneCompositionPercentaje(boneCompositionPercentaje);
        return this;
    }

    public void setBoneCompositionPercentaje(Float boneCompositionPercentaje) {
        this.boneCompositionPercentaje = boneCompositionPercentaje;
    }

    public Float getMuscleCompositionPercentaje() {
        return this.muscleCompositionPercentaje;
    }

    public UserBodyInfo muscleCompositionPercentaje(Float muscleCompositionPercentaje) {
        this.setMuscleCompositionPercentaje(muscleCompositionPercentaje);
        return this;
    }

    public void setMuscleCompositionPercentaje(Float muscleCompositionPercentaje) {
        this.muscleCompositionPercentaje = muscleCompositionPercentaje;
    }

    public Boolean getSmoker() {
        return this.smoker;
    }

    public UserBodyInfo smoker(Boolean smoker) {
        this.setSmoker(smoker);
        return this;
    }

    public void setSmoker(Boolean smoker) {
        this.smoker = smoker;
    }

    public Float getWaightKg() {
        return this.waightKg;
    }

    public UserBodyInfo waightKg(Float waightKg) {
        this.setWaightKg(waightKg);
        return this;
    }

    public void setWaightKg(Float waightKg) {
        this.waightKg = waightKg;
    }

    public Float getHeightCm() {
        return this.heightCm;
    }

    public UserBodyInfo heightCm(Float heightCm) {
        this.setHeightCm(heightCm);
        return this;
    }

    public void setHeightCm(Float heightCm) {
        this.heightCm = heightCm;
    }

    public Float getBodyHealthScore() {
        return this.bodyHealthScore;
    }

    public UserBodyInfo bodyHealthScore(Float bodyHealthScore) {
        this.setBodyHealthScore(bodyHealthScore);
        return this;
    }

    public void setBodyHealthScore(Float bodyHealthScore) {
        this.bodyHealthScore = bodyHealthScore;
    }

    public Integer getCardiovascularRisk() {
        return this.cardiovascularRisk;
    }

    public UserBodyInfo cardiovascularRisk(Integer cardiovascularRisk) {
        this.setCardiovascularRisk(cardiovascularRisk);
        return this;
    }

    public void setCardiovascularRisk(Integer cardiovascularRisk) {
        this.cardiovascularRisk = cardiovascularRisk;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserBodyInfo)) {
            return false;
        }
        return id != null && id.equals(((UserBodyInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserBodyInfo{" +
            "id=" + getId() +
            ", usuarioId='" + getUsuarioId() + "'" +
            ", empresaId='" + getEmpresaId() + "'" +
            ", waistCircumference=" + getWaistCircumference() +
            ", hipCircumference=" + getHipCircumference() +
            ", chestCircumference=" + getChestCircumference() +
            ", boneCompositionPercentaje=" + getBoneCompositionPercentaje() +
            ", muscleCompositionPercentaje=" + getMuscleCompositionPercentaje() +
            ", smoker='" + getSmoker() + "'" +
            ", waightKg=" + getWaightKg() +
            ", heightCm=" + getHeightCm() +
            ", bodyHealthScore=" + getBodyHealthScore() +
            ", cardiovascularRisk=" + getCardiovascularRisk() +
            "}";
    }
}
