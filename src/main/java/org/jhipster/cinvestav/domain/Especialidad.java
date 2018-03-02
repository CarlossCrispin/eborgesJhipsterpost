package org.jhipster.cinvestav.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Especialidad.
 */
@Entity
@Table(name = "especialidad")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "especialidad")
public class Especialidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nespecialidad")
    private String nespecialidad;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNespecialidad() {
        return nespecialidad;
    }

    public Especialidad nespecialidad(String nespecialidad) {
        this.nespecialidad = nespecialidad;
        return this;
    }

    public void setNespecialidad(String nespecialidad) {
        this.nespecialidad = nespecialidad;
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
        Especialidad especialidad = (Especialidad) o;
        if (especialidad.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), especialidad.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Especialidad{" +
            "id=" + getId() +
            ", nespecialidad='" + getNespecialidad() + "'" +
            "}";
    }
}
