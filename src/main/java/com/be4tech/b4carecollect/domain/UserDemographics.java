package com.be4tech.b4carecollect.domain;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UserDemographics.
 */
@Entity
@Table(name = "user_demographics")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserDemographics implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "usuario_id")
    private String usuarioId;

    @Column(name = "empresa_id")
    private String empresaId;

    @Column(name = "gender")
    private String gender;

    @Column(name = "date_of_bird")
    private LocalDate dateOfBird;

    @Column(name = "age")
    private Integer age;

    @Column(name = "country")
    private String country;

    @Column(name = "state")
    private String state;

    @Column(name = "city")
    private String city;

    @Column(name = "ethnicity")
    private String ethnicity;

    @Column(name = "income")
    private String income;

    @Column(name = "marital_status")
    private String maritalStatus;

    @Column(name = "education")
    private String education;

    @Column(name = "end_time")
    private Instant endTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public UserDemographics id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return this.usuarioId;
    }

    public UserDemographics usuarioId(String usuarioId) {
        this.setUsuarioId(usuarioId);
        return this;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEmpresaId() {
        return this.empresaId;
    }

    public UserDemographics empresaId(String empresaId) {
        this.setEmpresaId(empresaId);
        return this;
    }

    public void setEmpresaId(String empresaId) {
        this.empresaId = empresaId;
    }

    public String getGender() {
        return this.gender;
    }

    public UserDemographics gender(String gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBird() {
        return this.dateOfBird;
    }

    public UserDemographics dateOfBird(LocalDate dateOfBird) {
        this.setDateOfBird(dateOfBird);
        return this;
    }

    public void setDateOfBird(LocalDate dateOfBird) {
        this.dateOfBird = dateOfBird;
    }

    public Integer getAge() {
        return this.age;
    }

    public UserDemographics age(Integer age) {
        this.setAge(age);
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCountry() {
        return this.country;
    }

    public UserDemographics country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return this.state;
    }

    public UserDemographics state(String state) {
        this.setState(state);
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return this.city;
    }

    public UserDemographics city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEthnicity() {
        return this.ethnicity;
    }

    public UserDemographics ethnicity(String ethnicity) {
        this.setEthnicity(ethnicity);
        return this;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getIncome() {
        return this.income;
    }

    public UserDemographics income(String income) {
        this.setIncome(income);
        return this;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getMaritalStatus() {
        return this.maritalStatus;
    }

    public UserDemographics maritalStatus(String maritalStatus) {
        this.setMaritalStatus(maritalStatus);
        return this;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getEducation() {
        return this.education;
    }

    public UserDemographics education(String education) {
        this.setEducation(education);
        return this;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    public UserDemographics endTime(Instant endTime) {
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
        if (!(o instanceof UserDemographics)) {
            return false;
        }
        return id != null && id.equals(((UserDemographics) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserDemographics{" +
            "id=" + getId() +
            ", usuarioId='" + getUsuarioId() + "'" +
            ", empresaId='" + getEmpresaId() + "'" +
            ", gender='" + getGender() + "'" +
            ", dateOfBird='" + getDateOfBird() + "'" +
            ", age=" + getAge() +
            ", country='" + getCountry() + "'" +
            ", state='" + getState() + "'" +
            ", city='" + getCity() + "'" +
            ", ethnicity='" + getEthnicity() + "'" +
            ", income='" + getIncome() + "'" +
            ", maritalStatus='" + getMaritalStatus() + "'" +
            ", education='" + getEducation() + "'" +
            ", endTime='" + getEndTime() + "'" +
            "}";
    }
}
