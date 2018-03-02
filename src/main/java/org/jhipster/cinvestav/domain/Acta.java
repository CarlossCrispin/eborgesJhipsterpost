package org.jhipster.cinvestav.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Acta.
 */
@Entity
@Table(name = "acta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "acta")
public class Acta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "folio")
    private Long folio;

    @Column(name = "fechatomagrado")
    private Instant fechatomagrado;

    @ManyToOne
    private Tesis tesis;

    @ManyToOne
    private Alumno alumno;

    @ManyToOne
    private Unidad unidad;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFolio() {
        return folio;
    }

    public Acta folio(Long folio) {
        this.folio = folio;
        return this;
    }

    public void setFolio(Long folio) {
        this.folio = folio;
    }

    public Instant getFechatomagrado() {
        return fechatomagrado;
    }

    public Acta fechatomagrado(Instant fechatomagrado) {
        this.fechatomagrado = fechatomagrado;
        return this;
    }

    public void setFechatomagrado(Instant fechatomagrado) {
        this.fechatomagrado = fechatomagrado;
    }

    public Tesis getTesis() {
        return tesis;
    }

    public Acta tesis(Tesis tesis) {
        this.tesis = tesis;
        return this;
    }

    public void setTesis(Tesis tesis) {
        this.tesis = tesis;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public Acta alumno(Alumno alumno) {
        this.alumno = alumno;
        return this;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public Acta unidad(Unidad unidad) {
        this.unidad = unidad;
        return this;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
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
        Acta acta = (Acta) o;
        if (acta.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), acta.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Acta{" +
            "id=" + getId() +
            ", folio=" + getFolio() +
            ", fechatomagrado='" + getFechatomagrado() + "'" +
            "}";
    }
}
