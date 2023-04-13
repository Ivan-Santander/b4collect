package com.be4tech.b4carecollect.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A Nutrition.
 */
@Entity
@Table(name = "nutrition")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Nutrition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "usuario_id")
    private String usuarioId;

    @Column(name = "empresa_id")
    private String empresaId;

    @Column(name = "meal_type")
    private Integer mealType;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "food")
    private String food;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "nutrients")
    private String nutrients;

    @Column(name = "end_time")
    private Instant endTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Nutrition id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return this.usuarioId;
    }

    public Nutrition usuarioId(String usuarioId) {
        this.setUsuarioId(usuarioId);
        return this;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEmpresaId() {
        return this.empresaId;
    }

    public Nutrition empresaId(String empresaId) {
        this.setEmpresaId(empresaId);
        return this;
    }

    public void setEmpresaId(String empresaId) {
        this.empresaId = empresaId;
    }

    public Integer getMealType() {
        return this.mealType;
    }

    public Nutrition mealType(Integer mealType) {
        this.setMealType(mealType);
        return this;
    }

    public void setMealType(Integer mealType) {
        this.mealType = mealType;
    }

    public String getFood() {
        return this.food;
    }

    public Nutrition food(String food) {
        this.setFood(food);
        return this;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getNutrients() {
        return this.nutrients;
    }

    public Nutrition nutrients(String nutrients) {
        this.setNutrients(nutrients);
        return this;
    }

    public void setNutrients(String nutrients) {
        this.nutrients = nutrients;
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    public Nutrition endTime(Instant endTime) {
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
        if (!(o instanceof Nutrition)) {
            return false;
        }
        return id != null && id.equals(((Nutrition) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Nutrition{" +
            "id=" + getId() +
            ", usuarioId='" + getUsuarioId() + "'" +
            ", empresaId='" + getEmpresaId() + "'" +
            ", mealType=" + getMealType() +
            ", food='" + getFood() + "'" +
            ", nutrients='" + getNutrients() + "'" +
            ", endTime='" + getEndTime() + "'" +
            "}";
    }
}
