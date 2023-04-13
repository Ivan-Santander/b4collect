package com.be4tech.b4carecollect.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.be4tech.b4carecollect.domain.UserDemographics} entity. This class is used
 * in {@link com.be4tech.b4carecollect.web.rest.UserDemographicsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /user-demographics?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserDemographicsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private StringFilter usuarioId;

    private StringFilter empresaId;

    private StringFilter gender;

    private LocalDateFilter dateOfBird;

    private IntegerFilter age;

    private StringFilter country;

    private StringFilter state;

    private StringFilter city;

    private StringFilter ethnicity;

    private StringFilter income;

    private StringFilter maritalStatus;

    private StringFilter education;

    private InstantFilter endTime;

    private Boolean distinct;

    public UserDemographicsCriteria() {}

    public UserDemographicsCriteria(UserDemographicsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.dateOfBird = other.dateOfBird == null ? null : other.dateOfBird.copy();
        this.age = other.age == null ? null : other.age.copy();
        this.country = other.country == null ? null : other.country.copy();
        this.state = other.state == null ? null : other.state.copy();
        this.city = other.city == null ? null : other.city.copy();
        this.ethnicity = other.ethnicity == null ? null : other.ethnicity.copy();
        this.income = other.income == null ? null : other.income.copy();
        this.maritalStatus = other.maritalStatus == null ? null : other.maritalStatus.copy();
        this.education = other.education == null ? null : other.education.copy();
        this.endTime = other.endTime == null ? null : other.endTime.copy();
        this.distinct = other.distinct;
    }

    @Override
    public UserDemographicsCriteria copy() {
        return new UserDemographicsCriteria(this);
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

    public StringFilter getGender() {
        return gender;
    }

    public StringFilter gender() {
        if (gender == null) {
            gender = new StringFilter();
        }
        return gender;
    }

    public void setGender(StringFilter gender) {
        this.gender = gender;
    }

    public LocalDateFilter getDateOfBird() {
        return dateOfBird;
    }

    public LocalDateFilter dateOfBird() {
        if (dateOfBird == null) {
            dateOfBird = new LocalDateFilter();
        }
        return dateOfBird;
    }

    public void setDateOfBird(LocalDateFilter dateOfBird) {
        this.dateOfBird = dateOfBird;
    }

    public IntegerFilter getAge() {
        return age;
    }

    public IntegerFilter age() {
        if (age == null) {
            age = new IntegerFilter();
        }
        return age;
    }

    public void setAge(IntegerFilter age) {
        this.age = age;
    }

    public StringFilter getCountry() {
        return country;
    }

    public StringFilter country() {
        if (country == null) {
            country = new StringFilter();
        }
        return country;
    }

    public void setCountry(StringFilter country) {
        this.country = country;
    }

    public StringFilter getState() {
        return state;
    }

    public StringFilter state() {
        if (state == null) {
            state = new StringFilter();
        }
        return state;
    }

    public void setState(StringFilter state) {
        this.state = state;
    }

    public StringFilter getCity() {
        return city;
    }

    public StringFilter city() {
        if (city == null) {
            city = new StringFilter();
        }
        return city;
    }

    public void setCity(StringFilter city) {
        this.city = city;
    }

    public StringFilter getEthnicity() {
        return ethnicity;
    }

    public StringFilter ethnicity() {
        if (ethnicity == null) {
            ethnicity = new StringFilter();
        }
        return ethnicity;
    }

    public void setEthnicity(StringFilter ethnicity) {
        this.ethnicity = ethnicity;
    }

    public StringFilter getIncome() {
        return income;
    }

    public StringFilter income() {
        if (income == null) {
            income = new StringFilter();
        }
        return income;
    }

    public void setIncome(StringFilter income) {
        this.income = income;
    }

    public StringFilter getMaritalStatus() {
        return maritalStatus;
    }

    public StringFilter maritalStatus() {
        if (maritalStatus == null) {
            maritalStatus = new StringFilter();
        }
        return maritalStatus;
    }

    public void setMaritalStatus(StringFilter maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public StringFilter getEducation() {
        return education;
    }

    public StringFilter education() {
        if (education == null) {
            education = new StringFilter();
        }
        return education;
    }

    public void setEducation(StringFilter education) {
        this.education = education;
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
        final UserDemographicsCriteria that = (UserDemographicsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(dateOfBird, that.dateOfBird) &&
            Objects.equals(age, that.age) &&
            Objects.equals(country, that.country) &&
            Objects.equals(state, that.state) &&
            Objects.equals(city, that.city) &&
            Objects.equals(ethnicity, that.ethnicity) &&
            Objects.equals(income, that.income) &&
            Objects.equals(maritalStatus, that.maritalStatus) &&
            Objects.equals(education, that.education) &&
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
            gender,
            dateOfBird,
            age,
            country,
            state,
            city,
            ethnicity,
            income,
            maritalStatus,
            education,
            endTime,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserDemographicsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            (gender != null ? "gender=" + gender + ", " : "") +
            (dateOfBird != null ? "dateOfBird=" + dateOfBird + ", " : "") +
            (age != null ? "age=" + age + ", " : "") +
            (country != null ? "country=" + country + ", " : "") +
            (state != null ? "state=" + state + ", " : "") +
            (city != null ? "city=" + city + ", " : "") +
            (ethnicity != null ? "ethnicity=" + ethnicity + ", " : "") +
            (income != null ? "income=" + income + ", " : "") +
            (maritalStatus != null ? "maritalStatus=" + maritalStatus + ", " : "") +
            (education != null ? "education=" + education + ", " : "") +
            (endTime != null ? "endTime=" + endTime + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
