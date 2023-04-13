package com.be4tech.b4carecollect.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.be4tech.b4carecollect.domain.ActivityExercise} entity. This class is used
 * in {@link com.be4tech.b4carecollect.web.rest.ActivityExerciseResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /activity-exercises?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ActivityExerciseCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private StringFilter usuarioId;

    private StringFilter empresaId;

    private StringFilter exercise;

    private IntegerFilter repetitions;

    private StringFilter typeResistence;

    private FloatFilter resistenceKg;

    private IntegerFilter duration;

    private InstantFilter startTime;

    private InstantFilter endTime;

    private Boolean distinct;

    public ActivityExerciseCriteria() {}

    public ActivityExerciseCriteria(ActivityExerciseCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.exercise = other.exercise == null ? null : other.exercise.copy();
        this.repetitions = other.repetitions == null ? null : other.repetitions.copy();
        this.typeResistence = other.typeResistence == null ? null : other.typeResistence.copy();
        this.resistenceKg = other.resistenceKg == null ? null : other.resistenceKg.copy();
        this.duration = other.duration == null ? null : other.duration.copy();
        this.startTime = other.startTime == null ? null : other.startTime.copy();
        this.endTime = other.endTime == null ? null : other.endTime.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ActivityExerciseCriteria copy() {
        return new ActivityExerciseCriteria(this);
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

    public StringFilter getExercise() {
        return exercise;
    }

    public StringFilter exercise() {
        if (exercise == null) {
            exercise = new StringFilter();
        }
        return exercise;
    }

    public void setExercise(StringFilter exercise) {
        this.exercise = exercise;
    }

    public IntegerFilter getRepetitions() {
        return repetitions;
    }

    public IntegerFilter repetitions() {
        if (repetitions == null) {
            repetitions = new IntegerFilter();
        }
        return repetitions;
    }

    public void setRepetitions(IntegerFilter repetitions) {
        this.repetitions = repetitions;
    }

    public StringFilter getTypeResistence() {
        return typeResistence;
    }

    public StringFilter typeResistence() {
        if (typeResistence == null) {
            typeResistence = new StringFilter();
        }
        return typeResistence;
    }

    public void setTypeResistence(StringFilter typeResistence) {
        this.typeResistence = typeResistence;
    }

    public FloatFilter getResistenceKg() {
        return resistenceKg;
    }

    public FloatFilter resistenceKg() {
        if (resistenceKg == null) {
            resistenceKg = new FloatFilter();
        }
        return resistenceKg;
    }

    public void setResistenceKg(FloatFilter resistenceKg) {
        this.resistenceKg = resistenceKg;
    }

    public IntegerFilter getDuration() {
        return duration;
    }

    public IntegerFilter duration() {
        if (duration == null) {
            duration = new IntegerFilter();
        }
        return duration;
    }

    public void setDuration(IntegerFilter duration) {
        this.duration = duration;
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
        final ActivityExerciseCriteria that = (ActivityExerciseCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(exercise, that.exercise) &&
            Objects.equals(repetitions, that.repetitions) &&
            Objects.equals(typeResistence, that.typeResistence) &&
            Objects.equals(resistenceKg, that.resistenceKg) &&
            Objects.equals(duration, that.duration) &&
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
            exercise,
            repetitions,
            typeResistence,
            resistenceKg,
            duration,
            startTime,
            endTime,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActivityExerciseCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            (exercise != null ? "exercise=" + exercise + ", " : "") +
            (repetitions != null ? "repetitions=" + repetitions + ", " : "") +
            (typeResistence != null ? "typeResistence=" + typeResistence + ", " : "") +
            (resistenceKg != null ? "resistenceKg=" + resistenceKg + ", " : "") +
            (duration != null ? "duration=" + duration + ", " : "") +
            (startTime != null ? "startTime=" + startTime + ", " : "") +
            (endTime != null ? "endTime=" + endTime + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
