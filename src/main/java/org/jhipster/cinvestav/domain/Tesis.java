package org.jhipster.cinvestav.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Tesis.
 */
@Entity
@Table(name = "tesis")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tesis")
public class Tesis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "titulodetesis")
    private String titulodetesis;

    @Column(name = "fechadepublicacion")
    private Instant fechadepublicacion;

    @Column(name = "resumen")
    private String resumen;

    @Column(name = "clasificacion")
    private String clasificacion;

    @Column(name = "clasificacion_1")
    private String clasificacion1;

    @Column(name = "anio")
    private Long anio;

    @Column(name = "mes")
    private Long mes;

    @ManyToOne
    private Alumno alumno;

    @ManyToOne
    private Grado grado;

    @ManyToOne
    private Departamento departamento;

    @ManyToOne
    private Unidad unidad;

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

    public String getTitulodetesis() {
        return titulodetesis;
    }

    public Tesis titulodetesis(String titulodetesis) {
        this.titulodetesis = titulodetesis;
        return this;
    }

    public void setTitulodetesis(String titulodetesis) {
        this.titulodetesis = titulodetesis;
    }

    public Instant getFechadepublicacion() {
        return fechadepublicacion;
    }

    public Tesis fechadepublicacion(Instant fechadepublicacion) {
        this.fechadepublicacion = fechadepublicacion;
        return this;
    }

    public void setFechadepublicacion(Instant fechadepublicacion) {
        this.fechadepublicacion = fechadepublicacion;
    }

    public String getResumen() {
        return resumen;
    }

    public Tesis resumen(String resumen) {
        this.resumen = resumen;
        return this;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public Tesis clasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
        return this;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getClasificacion1() {
        return clasificacion1;
    }

    public Tesis clasificacion1(String clasificacion1) {
        this.clasificacion1 = clasificacion1;
        return this;
    }

    public void setClasificacion1(String clasificacion1) {
        this.clasificacion1 = clasificacion1;
    }

    public Long getAnio() {
        return anio;
    }

    public Tesis anio(Long anio) {
        this.anio = anio;
        return this;
    }

    public void setAnio(Long anio) {
        this.anio = anio;
    }

    public Long getMes() {
        return mes;
    }

    public Tesis mes(Long mes) {
        this.mes = mes;
        return this;
    }

    public void setMes(Long mes) {
        this.mes = mes;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public Tesis alumno(Alumno alumno) {
        this.alumno = alumno;
        return this;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Grado getGrado() {
        return grado;
    }

    public Tesis grado(Grado grado) {
        this.grado = grado;
        return this;
    }

    public void setGrado(Grado grado) {
        this.grado = grado;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public Tesis departamento(Departamento departamento) {
        this.departamento = departamento;
        return this;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public Tesis unidad(Unidad unidad) {
        this.unidad = unidad;
        return this;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public Investigador getInvestigador1() {
        return investigador1;
    }

    public Tesis investigador1(Investigador investigador) {
        this.investigador1 = investigador;
        return this;
    }

    public void setInvestigador1(Investigador investigador) {
        this.investigador1 = investigador;
    }

    public TipoAsesor getTipoasesor1() {
        return tipoasesor1;
    }

    public Tesis tipoasesor1(TipoAsesor tipoAsesor) {
        this.tipoasesor1 = tipoAsesor;
        return this;
    }

    public void setTipoasesor1(TipoAsesor tipoAsesor) {
        this.tipoasesor1 = tipoAsesor;
    }

    public Investigador getInvestigador2() {
        return investigador2;
    }

    public Tesis investigador2(Investigador investigador) {
        this.investigador2 = investigador;
        return this;
    }

    public void setInvestigador2(Investigador investigador) {
        this.investigador2 = investigador;
    }

    public TipoAsesor getTipoasesor2() {
        return tipoasesor2;
    }

    public Tesis tipoasesor2(TipoAsesor tipoAsesor) {
        this.tipoasesor2 = tipoAsesor;
        return this;
    }

    public void setTipoasesor2(TipoAsesor tipoAsesor) {
        this.tipoasesor2 = tipoAsesor;
    }

    public Investigador getInvestigador3() {
        return investigador3;
    }

    public Tesis investigador3(Investigador investigador) {
        this.investigador3 = investigador;
        return this;
    }

    public void setInvestigador3(Investigador investigador) {
        this.investigador3 = investigador;
    }

    public TipoAsesor getTipoasesor3() {
        return tipoasesor3;
    }

    public Tesis tipoasesor3(TipoAsesor tipoAsesor) {
        this.tipoasesor3 = tipoAsesor;
        return this;
    }

    public void setTipoasesor3(TipoAsesor tipoAsesor) {
        this.tipoasesor3 = tipoAsesor;
    }

    public Investigador getInvestigador4() {
        return investigador4;
    }

    public Tesis investigador4(Investigador investigador) {
        this.investigador4 = investigador;
        return this;
    }

    public void setInvestigador4(Investigador investigador) {
        this.investigador4 = investigador;
    }

    public TipoAsesor getTipoasesor4() {
        return tipoasesor4;
    }

    public Tesis tipoasesor4(TipoAsesor tipoAsesor) {
        this.tipoasesor4 = tipoAsesor;
        return this;
    }

    public void setTipoasesor4(TipoAsesor tipoAsesor) {
        this.tipoasesor4 = tipoAsesor;
    }

    public Investigador getInvestigador5() {
        return investigador5;
    }

    public Tesis investigador5(Investigador investigador) {
        this.investigador5 = investigador;
        return this;
    }

    public void setInvestigador5(Investigador investigador) {
        this.investigador5 = investigador;
    }

    public TipoAsesor getTipoasesor5() {
        return tipoasesor5;
    }

    public Tesis tipoasesor5(TipoAsesor tipoAsesor) {
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
        Tesis tesis = (Tesis) o;
        if (tesis.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tesis.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tesis{" +
            "id=" + getId() +
            ", titulodetesis='" + getTitulodetesis() + "'" +
            ", fechadepublicacion='" + getFechadepublicacion() + "'" +
            ", resumen='" + getResumen() + "'" +
            ", clasificacion='" + getClasificacion() + "'" +
            ", clasificacion1='" + getClasificacion1() + "'" +
            ", anio=" + getAnio() +
            ", mes=" + getMes() +
            "}";
    }
}
