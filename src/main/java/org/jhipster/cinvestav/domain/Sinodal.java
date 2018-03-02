package org.jhipster.cinvestav.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Sinodal.
 */
@Entity
@Table(name = "sinodal")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "sinodal")
public class Sinodal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    private Acta acta;

    @ManyToOne
    private TipoAsesor tipoasesor;

    @ManyToOne
    private Investigador investigador;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Acta getActa() {
        return acta;
    }

    public Sinodal acta(Acta acta) {
        this.acta = acta;
        return this;
    }

    public void setActa(Acta acta) {
        this.acta = acta;
    }

    public TipoAsesor getTipoasesor() {
        return tipoasesor;
    }

    public Sinodal tipoasesor(TipoAsesor tipoAsesor) {
        this.tipoasesor = tipoAsesor;
        return this;
    }

    public void setTipoasesor(TipoAsesor tipoAsesor) {
        this.tipoasesor = tipoAsesor;
    }

    public Investigador getInvestigador() {
        return investigador;
    }

    public Sinodal investigador(Investigador investigador) {
        this.investigador = investigador;
        return this;
    }

    public void setInvestigador(Investigador investigador) {
        this.investigador = investigador;
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
        Sinodal sinodal = (Sinodal) o;
        if (sinodal.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sinodal.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Sinodal{" +
            "id=" + getId() +
            "}";
    }
}
