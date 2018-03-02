package org.jhipster.cinvestav.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Grado.
 */
@Entity
@Table(name = "grado")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "grado")
public class Grado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ngrado")
    private String ngrado;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNgrado() {
        return ngrado;
    }

    public Grado ngrado(String ngrado) {
        this.ngrado = ngrado;
        return this;
    }

    public void setNgrado(String ngrado) {
        this.ngrado = ngrado;
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
        Grado grado = (Grado) o;
        if (grado.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), grado.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Grado{" +
            "id=" + getId() +
            ", ngrado='" + getNgrado() + "'" +
            "}";
    }
}
