package org.jhipster.cinvestav.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Unidad.
 */
@Entity
@Table(name = "unidad")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "unidad")
public class Unidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nunidad")
    private String nunidad;

    @Column(name = "ubicacion")
    private String ubicacion;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNunidad() {
        return nunidad;
    }

    public Unidad nunidad(String nunidad) {
        this.nunidad = nunidad;
        return this;
    }

    public void setNunidad(String nunidad) {
        this.nunidad = nunidad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public Unidad ubicacion(String ubicacion) {
        this.ubicacion = ubicacion;
        return this;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
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
        Unidad unidad = (Unidad) o;
        if (unidad.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), unidad.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Unidad{" +
            "id=" + getId() +
            ", nunidad='" + getNunidad() + "'" +
            ", ubicacion='" + getUbicacion() + "'" +
            "}";
    }
}
