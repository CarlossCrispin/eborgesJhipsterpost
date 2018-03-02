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
