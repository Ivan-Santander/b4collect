package com.be4tech.b4carecollect.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.be4tech.b4carecollect.domain.LocationSample} entity. This class is used
 * in {@link com.be4tech.b4carecollect.web.rest.LocationSampleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /location-samples?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LocationSampleCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private StringFilter usuarioId;

    private StringFilter empresaId;

    private FloatFilter latitudMin;

    private FloatFilter longitudMin;

    private FloatFilter latitudMax;

    private FloatFilter longitudMax;

    private FloatFilter accuracy;

    private FloatFilter altitud;

    private InstantFilter startTime;

    private InstantFilter endTime;

    private Boolean distinct;

    public LocationSampleCriteria() {}

    public LocationSampleCriteria(LocationSampleCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.latitudMin = other.latitudMin == null ? null : other.latitudMin.copy();
        this.longitudMin = other.longitudMin == null ? null : other.longitudMin.copy();
        this.latitudMax = other.latitudMax == null ? null : other.latitudMax.copy();
        this.longitudMax = other.longitudMax == null ? null : other.longitudMax.copy();
        this.accuracy = other.accuracy == null ? null : other.accuracy.copy();
        this.altitud = other.altitud == null ? null : other.altitud.copy();
        this.startTime = other.startTime == null ? null : other.startTime.copy();
        this.endTime = other.endTime == null ? null : other.endTime.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LocationSampleCriteria copy() {
        return new LocationSampleCriteria(this);
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

    public FloatFilter getLatitudMin() {
        return latitudMin;
    }

    public FloatFilter latitudMin() {
        if (latitudMin == null) {
            latitudMin = new FloatFilter();
        }
        return latitudMin;
    }

    public void setLatitudMin(FloatFilter latitudMin) {
        this.latitudMin = latitudMin;
    }

    public FloatFilter getLongitudMin() {
        return longitudMin;
    }

    public FloatFilter longitudMin() {
        if (longitudMin == null) {
            longitudMin = new FloatFilter();
        }
        return longitudMin;
    }

    public void setLongitudMin(FloatFilter longitudMin) {
        this.longitudMin = longitudMin;
    }

    public FloatFilter getLatitudMax() {
        return latitudMax;
    }

    public FloatFilter latitudMax() {
        if (latitudMax == null) {
            latitudMax = new FloatFilter();
        }
        return latitudMax;
    }

    public void setLatitudMax(FloatFilter latitudMax) {
        this.latitudMax = latitudMax;
    }

    public FloatFilter getLongitudMax() {
        return longitudMax;
    }

    public FloatFilter longitudMax() {
        if (longitudMax == null) {
            longitudMax = new FloatFilter();
        }
        return longitudMax;
    }

    public void setLongitudMax(FloatFilter longitudMax) {
        this.longitudMax = longitudMax;
    }

    public FloatFilter getAccuracy() {
        return accuracy;
    }

    public FloatFilter accuracy() {
        if (accuracy == null) {
            accuracy = new FloatFilter();
        }
        return accuracy;
    }

    public void setAccuracy(FloatFilter accuracy) {
        this.accuracy = accuracy;
    }

    public FloatFilter getAltitud() {
        return altitud;
    }

    public FloatFilter altitud() {
        if (altitud == null) {
            altitud = new FloatFilter();
        }
        return altitud;
    }

    public void setAltitud(FloatFilter altitud) {
        this.altitud = altitud;
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
        final LocationSampleCriteria that = (LocationSampleCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(latitudMin, that.latitudMin) &&
            Objects.equals(longitudMin, that.longitudMin) &&
            Objects.equals(latitudMax, that.latitudMax) &&
            Objects.equals(longitudMax, that.longitudMax) &&
            Objects.equals(accuracy, that.accuracy) &&
            Objects.equals(altitud, that.altitud) &&
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
            latitudMin,
            longitudMin,
            latitudMax,
            longitudMax,
            accuracy,
            altitud,
            startTime,
            endTime,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LocationSampleCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            (latitudMin != null ? "latitudMin=" + latitudMin + ", " : "") +
            (longitudMin != null ? "longitudMin=" + longitudMin + ", " : "") +
            (latitudMax != null ? "latitudMax=" + latitudMax + ", " : "") +
            (longitudMax != null ? "longitudMax=" + longitudMax + ", " : "") +
            (accuracy != null ? "accuracy=" + accuracy + ", " : "") +
            (altitud != null ? "altitud=" + altitud + ", " : "") +
            (startTime != null ? "startTime=" + startTime + ", " : "") +
            (endTime != null ? "endTime=" + endTime + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
