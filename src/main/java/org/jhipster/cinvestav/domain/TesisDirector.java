package org.jhipster.cinvestav.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TesisDirector.
 */
@Entity
@Table(name = "tesis_director")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tesisdirector")
public class TesisDirector implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    private Tesis tesis;

    @ManyToOne
    private Investigador investigador;

    @ManyToOne
    private TipoAsesor tipoasesor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tesis getTesis() {
        return tesis;
    }

    public TesisDirector tesis(Tesis tesis) {
        this.tesis = tesis;
        return this;
    }

    public void setTesis(Tesis tesis) {
        this.tesis = tesis;
    }

    public Investigador getInvestigador() {
        return investigador;
    }

    public TesisDirector investigador(Investigador investigador) {
        this.investigador = investigador;
        return this;
    }

    public void setInvestigador(Investigador investigador) {
        this.investigador = investigador;
    }

    public TipoAsesor getTipoasesor() {
        return tipoasesor;
    }

    public TesisDirector tipoasesor(TipoAsesor tipoAsesor) {
        this.tipoasesor = tipoAsesor;
        return this;
    }

    public void setTipoasesor(TipoAsesor tipoAsesor) {
        this.tipoasesor = tipoAsesor;
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
        TesisDirector tesisDirector = (TesisDirector) o;
        if (tesisDirector.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tesisDirector.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TesisDirector{" +
            "id=" + getId() +
            "}";
    }
}
