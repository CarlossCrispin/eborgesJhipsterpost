package org.jhipster.cinvestav.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TipoAsesor.
 */
@Entity
@Table(name = "tipo_asesor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tipoasesor")
public class TipoAsesor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ntipo")
    private String ntipo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNtipo() {
        return ntipo;
    }

    public TipoAsesor ntipo(String ntipo) {
        this.ntipo = ntipo;
        return this;
    }

    public void setNtipo(String ntipo) {
        this.ntipo = ntipo;
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
        TipoAsesor tipoAsesor = (TipoAsesor) o;
        if (tipoAsesor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipoAsesor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipoAsesor{" +
            "id=" + getId() +
            ", ntipo='" + getNtipo() + "'" +
            "}";
    }
}
