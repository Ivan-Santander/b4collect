package com.be4tech.b4carecollect.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ActivityExercise.
 */
@Entity
@Table(name = "activity_exercise")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ActivityExercise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "usuario_id")
    private String usuarioId;

    @Column(name = "empresa_id")
    private String empresaId;

    @Column(name = "exercise")
    private String exercise;

    @Column(name = "repetitions")
    private Integer repetitions;

    @Column(name = "type_resistence")
    private String typeResistence;

    @Column(name = "resistence_kg")
    private Float resistenceKg;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public ActivityExercise id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return this.usuarioId;
    }

    public ActivityExercise usuarioId(String usuarioId) {
        this.setUsuarioId(usuarioId);
        return this;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEmpresaId() {
        return this.empresaId;
    }

    public ActivityExercise empresaId(String empresaId) {
        this.setEmpresaId(empresaId);
        return this;
    }

    public void setEmpresaId(String empresaId) {
        this.empresaId = empresaId;
    }

    public String getExercise() {
        return this.exercise;
    }

    public ActivityExercise exercise(String exercise) {
        this.setExercise(exercise);
        return this;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public Integer getRepetitions() {
        return this.repetitions;
    }

    public ActivityExercise repetitions(Integer repetitions) {
        this.setRepetitions(repetitions);
        return this;
    }

    public void setRepetitions(Integer repetitions) {
        this.repetitions = repetitions;
    }

    public String getTypeResistence() {
        return this.typeResistence;
    }

    public ActivityExercise typeResistence(String typeResistence) {
        this.setTypeResistence(typeResistence);
        return this;
    }

    public void setTypeResistence(String typeResistence) {
        this.typeResistence = typeResistence;
    }

    public Float getResistenceKg() {
        return this.resistenceKg;
    }

    public ActivityExercise resistenceKg(Float resistenceKg) {
        this.setResistenceKg(resistenceKg);
        return this;
    }

    public void setResistenceKg(Float resistenceKg) {
        this.resistenceKg = resistenceKg;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public ActivityExercise duration(Integer duration) {
        this.setDuration(duration);
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Instant getStartTime() {
        return this.startTime;
    }

    public ActivityExercise startTime(Instant startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    public ActivityExercise endTime(Instant endTime) {
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
        if (!(o instanceof ActivityExercise)) {
            return false;
        }
        return id != null && id.equals(((ActivityExercise) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActivityExercise{" +
            "id=" + getId() +
            ", usuarioId='" + getUsuarioId() + "'" +
            ", empresaId='" + getEmpresaId() + "'" +
            ", exercise='" + getExercise() + "'" +
            ", repetitions=" + getRepetitions() +
            ", typeResistence='" + getTypeResistence() + "'" +
            ", resistenceKg=" + getResistenceKg() +
            ", duration=" + getDuration() +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            "}";
    }
}
