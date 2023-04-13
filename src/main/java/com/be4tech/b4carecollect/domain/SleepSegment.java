package com.be4tech.b4carecollect.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SleepSegment.
 */
@Entity
@Table(name = "sleep_segment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SleepSegment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "usuario_id")
    private String usuarioId;

    @Column(name = "empresa_id")
    private String empresaId;

    @Column(name = "field_sleep_segment_type")
    private Integer fieldSleepSegmentType;

    @Column(name = "field_blood_glucose_specimen_source")
    private Integer fieldBloodGlucoseSpecimenSource;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public SleepSegment id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return this.usuarioId;
    }

    public SleepSegment usuarioId(String usuarioId) {
        this.setUsuarioId(usuarioId);
        return this;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEmpresaId() {
        return this.empresaId;
    }

    public SleepSegment empresaId(String empresaId) {
        this.setEmpresaId(empresaId);
        return this;
    }

    public void setEmpresaId(String empresaId) {
        this.empresaId = empresaId;
    }

    public Integer getFieldSleepSegmentType() {
        return this.fieldSleepSegmentType;
    }

    public SleepSegment fieldSleepSegmentType(Integer fieldSleepSegmentType) {
        this.setFieldSleepSegmentType(fieldSleepSegmentType);
        return this;
    }

    public void setFieldSleepSegmentType(Integer fieldSleepSegmentType) {
        this.fieldSleepSegmentType = fieldSleepSegmentType;
    }

    public Integer getFieldBloodGlucoseSpecimenSource() {
        return this.fieldBloodGlucoseSpecimenSource;
    }

    public SleepSegment fieldBloodGlucoseSpecimenSource(Integer fieldBloodGlucoseSpecimenSource) {
        this.setFieldBloodGlucoseSpecimenSource(fieldBloodGlucoseSpecimenSource);
        return this;
    }

    public void setFieldBloodGlucoseSpecimenSource(Integer fieldBloodGlucoseSpecimenSource) {
        this.fieldBloodGlucoseSpecimenSource = fieldBloodGlucoseSpecimenSource;
    }

    public Instant getStartTime() {
        return this.startTime;
    }

    public SleepSegment startTime(Instant startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    public SleepSegment endTime(Instant endTime) {
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
        if (!(o instanceof SleepSegment)) {
            return false;
        }
        return id != null && id.equals(((SleepSegment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SleepSegment{" +
            "id=" + getId() +
            ", usuarioId='" + getUsuarioId() + "'" +
            ", empresaId='" + getEmpresaId() + "'" +
            ", fieldSleepSegmentType=" + getFieldSleepSegmentType() +
            ", fieldBloodGlucoseSpecimenSource=" + getFieldBloodGlucoseSpecimenSource() +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            "}";
    }
}
