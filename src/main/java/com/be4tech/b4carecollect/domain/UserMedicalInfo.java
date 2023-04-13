package com.be4tech.b4carecollect.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UserMedicalInfo.
 */
@Entity
@Table(name = "user_medical_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserMedicalInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "usuario_id")
    private String usuarioId;

    @Column(name = "empresa_id")
    private String empresaId;

    @Column(name = "hypertension")
    private Boolean hypertension;

    @Column(name = "high_glucose")
    private Boolean highGlucose;

    @Column(name = "hiabetes")
    private Boolean hiabetes;

    @Column(name = "total_cholesterol")
    private Float totalCholesterol;

    @Column(name = "hdl_cholesterol")
    private Float hdlCholesterol;

    @Column(name = "medical_main_condition")
    private String medicalMainCondition;

    @Column(name = "medical_secundary_condition")
    private String medicalSecundaryCondition;

    @Column(name = "medical_main_medication")
    private String medicalMainMedication;

    @Column(name = "medical_secundary_medication")
    private String medicalSecundaryMedication;

    @Column(name = "medical_score")
    private Integer medicalScore;

    @Column(name = "end_time")
    private Instant endTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public UserMedicalInfo id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return this.usuarioId;
    }

    public UserMedicalInfo usuarioId(String usuarioId) {
        this.setUsuarioId(usuarioId);
        return this;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEmpresaId() {
        return this.empresaId;
    }

    public UserMedicalInfo empresaId(String empresaId) {
        this.setEmpresaId(empresaId);
        return this;
    }

    public void setEmpresaId(String empresaId) {
        this.empresaId = empresaId;
    }

    public Boolean getHypertension() {
        return this.hypertension;
    }

    public UserMedicalInfo hypertension(Boolean hypertension) {
        this.setHypertension(hypertension);
        return this;
    }

    public void setHypertension(Boolean hypertension) {
        this.hypertension = hypertension;
    }

    public Boolean getHighGlucose() {
        return this.highGlucose;
    }

    public UserMedicalInfo highGlucose(Boolean highGlucose) {
        this.setHighGlucose(highGlucose);
        return this;
    }

    public void setHighGlucose(Boolean highGlucose) {
        this.highGlucose = highGlucose;
    }

    public Boolean getHiabetes() {
        return this.hiabetes;
    }

    public UserMedicalInfo hiabetes(Boolean hiabetes) {
        this.setHiabetes(hiabetes);
        return this;
    }

    public void setHiabetes(Boolean hiabetes) {
        this.hiabetes = hiabetes;
    }

    public Float getTotalCholesterol() {
        return this.totalCholesterol;
    }

    public UserMedicalInfo totalCholesterol(Float totalCholesterol) {
        this.setTotalCholesterol(totalCholesterol);
        return this;
    }

    public void setTotalCholesterol(Float totalCholesterol) {
        this.totalCholesterol = totalCholesterol;
    }

    public Float getHdlCholesterol() {
        return this.hdlCholesterol;
    }

    public UserMedicalInfo hdlCholesterol(Float hdlCholesterol) {
        this.setHdlCholesterol(hdlCholesterol);
        return this;
    }

    public void setHdlCholesterol(Float hdlCholesterol) {
        this.hdlCholesterol = hdlCholesterol;
    }

    public String getMedicalMainCondition() {
        return this.medicalMainCondition;
    }

    public UserMedicalInfo medicalMainCondition(String medicalMainCondition) {
        this.setMedicalMainCondition(medicalMainCondition);
        return this;
    }

    public void setMedicalMainCondition(String medicalMainCondition) {
        this.medicalMainCondition = medicalMainCondition;
    }

    public String getMedicalSecundaryCondition() {
        return this.medicalSecundaryCondition;
    }

    public UserMedicalInfo medicalSecundaryCondition(String medicalSecundaryCondition) {
        this.setMedicalSecundaryCondition(medicalSecundaryCondition);
        return this;
    }

    public void setMedicalSecundaryCondition(String medicalSecundaryCondition) {
        this.medicalSecundaryCondition = medicalSecundaryCondition;
    }

    public String getMedicalMainMedication() {
        return this.medicalMainMedication;
    }

    public UserMedicalInfo medicalMainMedication(String medicalMainMedication) {
        this.setMedicalMainMedication(medicalMainMedication);
        return this;
    }

    public void setMedicalMainMedication(String medicalMainMedication) {
        this.medicalMainMedication = medicalMainMedication;
    }

    public String getMedicalSecundaryMedication() {
        return this.medicalSecundaryMedication;
    }

    public UserMedicalInfo medicalSecundaryMedication(String medicalSecundaryMedication) {
        this.setMedicalSecundaryMedication(medicalSecundaryMedication);
        return this;
    }

    public void setMedicalSecundaryMedication(String medicalSecundaryMedication) {
        this.medicalSecundaryMedication = medicalSecundaryMedication;
    }

    public Integer getMedicalScore() {
        return this.medicalScore;
    }

    public UserMedicalInfo medicalScore(Integer medicalScore) {
        this.setMedicalScore(medicalScore);
        return this;
    }

    public void setMedicalScore(Integer medicalScore) {
        this.medicalScore = medicalScore;
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    public UserMedicalInfo endTime(Instant endTime) {
        this.setEndTime(endTime);
        return this;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserMedicalInfo)) {
            return false;
        }
        return id != null && id.equals(((UserMedicalInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserMedicalInfo{" +
            "id=" + getId() +
            ", usuarioId='" + getUsuarioId() + "'" +
            ", empresaId='" + getEmpresaId() + "'" +
            ", hypertension='" + getHypertension() + "'" +
            ", highGlucose='" + getHighGlucose() + "'" +
            ", hiabetes='" + getHiabetes() + "'" +
            ", totalCholesterol=" + getTotalCholesterol() +
            ", hdlCholesterol=" + getHdlCholesterol() +
            ", medicalMainCondition='" + getMedicalMainCondition() + "'" +
            ", medicalSecundaryCondition='" + getMedicalSecundaryCondition() + "'" +
            ", medicalMainMedication='" + getMedicalMainMedication() + "'" +
            ", medicalSecundaryMedication='" + getMedicalSecundaryMedication() + "'" +
            ", medicalScore=" + getMedicalScore() +
            ", endTime='" + getEndTime() + "'" +
            "}";
    }
}
