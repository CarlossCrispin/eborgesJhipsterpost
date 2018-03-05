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
    private Investigador investigador1;

    @ManyToOne
    private TipoAsesor tipoasesor1;

    @ManyToOne
    private Investigador investigador2;

    @ManyToOne
    private TipoAsesor tipoasesor2;

    @ManyToOne
    private Investigador investigador3;

    @ManyToOne
    private TipoAsesor tipoasesor3;

    @ManyToOne
    private Investigador investigador4;

    @ManyToOne
    private TipoAsesor tipoasesor4;

    @ManyToOne
    private Investigador investigador5;

    @ManyToOne
    private TipoAsesor tipoasesor5;

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

    public Investigador getInvestigador1() {
        return investigador1;
    }

    public TesisDirector investigador1(Investigador investigador) {
        this.investigador1 = investigador;
        return this;
    }

    public void setInvestigador1(Investigador investigador) {
        this.investigador1 = investigador;
    }

    public TipoAsesor getTipoasesor1() {
        return tipoasesor1;
    }

    public TesisDirector tipoasesor1(TipoAsesor tipoAsesor) {
        this.tipoasesor1 = tipoAsesor;
        return this;
    }

    public void setTipoasesor1(TipoAsesor tipoAsesor) {
        this.tipoasesor1 = tipoAsesor;
    }

    public Investigador getInvestigador2() {
        return investigador2;
    }

    public TesisDirector investigador2(Investigador investigador) {
        this.investigador2 = investigador;
        return this;
    }

    public void setInvestigador2(Investigador investigador) {
        this.investigador2 = investigador;
    }

    public TipoAsesor getTipoasesor2() {
        return tipoasesor2;
    }

    public TesisDirector tipoasesor2(TipoAsesor tipoAsesor) {
        this.tipoasesor2 = tipoAsesor;
        return this;
    }

    public void setTipoasesor2(TipoAsesor tipoAsesor) {
        this.tipoasesor2 = tipoAsesor;
    }

    public Investigador getInvestigador3() {
        return investigador3;
    }

    public TesisDirector investigador3(Investigador investigador) {
        this.investigador3 = investigador;
        return this;
    }

    public void setInvestigador3(Investigador investigador) {
        this.investigador3 = investigador;
    }

    public TipoAsesor getTipoasesor3() {
        return tipoasesor3;
    }

    public TesisDirector tipoasesor3(TipoAsesor tipoAsesor) {
        this.tipoasesor3 = tipoAsesor;
        return this;
    }

    public void setTipoasesor3(TipoAsesor tipoAsesor) {
        this.tipoasesor3 = tipoAsesor;
    }

    public Investigador getInvestigador4() {
        return investigador4;
    }

    public TesisDirector investigador4(Investigador investigador) {
        this.investigador4 = investigador;
        return this;
    }

    public void setInvestigador4(Investigador investigador) {
        this.investigador4 = investigador;
    }

    public TipoAsesor getTipoasesor4() {
        return tipoasesor4;
    }

    public TesisDirector tipoasesor4(TipoAsesor tipoAsesor) {
        this.tipoasesor4 = tipoAsesor;
        return this;
    }

    public void setTipoasesor4(TipoAsesor tipoAsesor) {
        this.tipoasesor4 = tipoAsesor;
    }

    public Investigador getInvestigador5() {
        return investigador5;
    }

    public TesisDirector investigador5(Investigador investigador) {
        this.investigador5 = investigador;
        return this;
    }

    public void setInvestigador5(Investigador investigador) {
        this.investigador5 = investigador;
    }

    public TipoAsesor getTipoasesor5() {
        return tipoasesor5;
    }

    public TesisDirector tipoasesor5(TipoAsesor tipoAsesor) {
        this.tipoasesor5 = tipoAsesor;
        return this;
    }

    public void setTipoasesor5(TipoAsesor tipoAsesor) {
        this.tipoasesor5 = tipoAsesor;
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
