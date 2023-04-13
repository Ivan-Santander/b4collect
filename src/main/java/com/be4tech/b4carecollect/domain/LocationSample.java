package com.be4tech.b4carecollect.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LocationSample.
 */
@Entity
@Table(name = "location_sample")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LocationSample implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "usuario_id")
    private String usuarioId;

    @Column(name = "empresa_id")
    private String empresaId;

    @Column(name = "latitud_min")
    private Float latitudMin;

    @Column(name = "longitud_min")
    private Float longitudMin;

    @Column(name = "latitud_max")
    private Float latitudMax;

    @Column(name = "longitud_max")
    private Float longitudMax;

    @Column(name = "accuracy")
    private Float accuracy;

    @Column(name = "altitud")
    private Float altitud;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public LocationSample id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return this.usuarioId;
    }

    public LocationSample usuarioId(String usuarioId) {
        this.setUsuarioId(usuarioId);
        return this;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEmpresaId() {
        return this.empresaId;
    }

    public LocationSample empresaId(String empresaId) {
        this.setEmpresaId(empresaId);
        return this;
    }

    public void setEmpresaId(String empresaId) {
        this.empresaId = empresaId;
    }

    public Float getLatitudMin() {
        return this.latitudMin;
    }

    public LocationSample latitudMin(Float latitudMin) {
        this.setLatitudMin(latitudMin);
        return this;
    }

    public void setLatitudMin(Float latitudMin) {
        this.latitudMin = latitudMin;
    }

    public Float getLongitudMin() {
        return this.longitudMin;
    }

    public LocationSample longitudMin(Float longitudMin) {
        this.setLongitudMin(longitudMin);
        return this;
    }

    public void setLongitudMin(Float longitudMin) {
        this.longitudMin = longitudMin;
    }

    public Float getLatitudMax() {
        return this.latitudMax;
    }

    public LocationSample latitudMax(Float latitudMax) {
        this.setLatitudMax(latitudMax);
        return this;
    }

    public void setLatitudMax(Float latitudMax) {
        this.latitudMax = latitudMax;
    }

    public Float getLongitudMax() {
        return this.longitudMax;
    }

    public LocationSample longitudMax(Float longitudMax) {
        this.setLongitudMax(longitudMax);
        return this;
    }

    public void setLongitudMax(Float longitudMax) {
        this.longitudMax = longitudMax;
    }

    public Float getAccuracy() {
        return this.accuracy;
    }

    public LocationSample accuracy(Float accuracy) {
        this.setAccuracy(accuracy);
        return this;
    }

    public void setAccuracy(Float accuracy) {
        this.accuracy = accuracy;
    }

    public Float getAltitud() {
        return this.altitud;
    }

    public LocationSample altitud(Float altitud) {
        this.setAltitud(altitud);
        return this;
    }

    public void setAltitud(Float altitud) {
        this.altitud = altitud;
    }

    public Instant getStartTime() {
        return this.startTime;
    }

    public LocationSample startTime(Instant startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    public LocationSample endTime(Instant endTime) {
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
        if (!(o instanceof LocationSample)) {
            return false;
        }
        return id != null && id.equals(((LocationSample) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LocationSample{" +
            "id=" + getId() +
            ", usuarioId='" + getUsuarioId() + "'" +
            ", empresaId='" + getEmpresaId() + "'" +
            ", latitudMin=" + getLatitudMin() +
            ", longitudMin=" + getLongitudMin() +
            ", latitudMax=" + getLatitudMax() +
            ", longitudMax=" + getLongitudMax() +
            ", accuracy=" + getAccuracy() +
            ", altitud=" + getAltitud() +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            "}";
    }
}
