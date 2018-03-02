package org.jhipster.cinvestav.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Departamento.
 */
@Entity
@Table(name = "departamento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "departamento")
public class Departamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ndepartamento")
    private String ndepartamento;

    @ManyToOne
    private Especialidad especialidad;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNdepartamento() {
        return ndepartamento;
    }

    public Departamento ndepartamento(String ndepartamento) {
        this.ndepartamento = ndepartamento;
        return this;
    }

    public void setNdepartamento(String ndepartamento) {
        this.ndepartamento = ndepartamento;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public Departamento especialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
        return this;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Departamento departamento = (Departamento) o;
        if (departamento.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), departamento.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Departamento{" +
            "id=" + getId() +
            ", ndepartamento='" + getNdepartamento() + "'" +
            "}";
    }
}
