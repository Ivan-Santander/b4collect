package com.be4tech.b4carecollect.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BodyTemperature.
 */
@Entity
@Table(name = "body_temperature")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BodyTemperature implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "usuario_id")
    private String usuarioId;

    @Column(name = "empresa_id")
    private String empresaId;

    @Column(name = "field_body_temperature")
    private Float fieldBodyTemperature;

    @Column(name = "field_body_temperature_measure_location")
    private Integer fieldBodyTemperatureMeasureLocation;

    @Column(name = "end_time")
    private Instant endTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public BodyTemperature id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return this.usuarioId;
    }

    public BodyTemperature usuarioId(String usuarioId) {
        this.setUsuarioId(usuarioId);
        return this;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEmpresaId() {
        return this.empresaId;
    }

    public BodyTemperature empresaId(String empresaId) {
        this.setEmpresaId(empresaId);
        return this;
    }

    public void setEmpresaId(String empresaId) {
        this.empresaId = empresaId;
    }

    public Float getFieldBodyTemperature() {
        return this.fieldBodyTemperature;
    }

    public BodyTemperature fieldBodyTemperature(Float fieldBodyTemperature) {
        this.setFieldBodyTemperature(fieldBodyTemperature);
        return this;
    }

    public void setFieldBodyTemperature(Float fieldBodyTemperature) {
        this.fieldBodyTemperature = fieldBodyTemperature;
    }

    public Integer getFieldBodyTemperatureMeasureLocation() {
        return this.fieldBodyTemperatureMeasureLocation;
    }

    public BodyTemperature fieldBodyTemperatureMeasureLocation(Integer fieldBodyTemperatureMeasureLocation) {
        this.setFieldBodyTemperatureMeasureLocation(fieldBodyTemperatureMeasureLocation);
        return this;
    }

    public void setFieldBodyTemperatureMeasureLocation(Integer fieldBodyTemperatureMeasureLocation) {
        this.fieldBodyTemperatureMeasureLocation = fieldBodyTemperatureMeasureLocation;
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    public BodyTemperature endTime(Instant endTime) {
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
        if (!(o instanceof BodyTemperature)) {
            return false;
        }
        return id != null && id.equals(((BodyTemperature) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BodyTemperature{" +
            "id=" + getId() +
            ", usuarioId='" + getUsuarioId() + "'" +
            ", empresaId='" + getEmpresaId() + "'" +
            ", fieldBodyTemperature=" + getFieldBodyTemperature() +
            ", fieldBodyTemperatureMeasureLocation=" + getFieldBodyTemperatureMeasureLocation() +
            ", endTime='" + getEndTime() + "'" +
            "}";
    }
}
