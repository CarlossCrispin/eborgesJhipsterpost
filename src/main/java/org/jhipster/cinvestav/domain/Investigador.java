package org.jhipster.cinvestav.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Investigador.
 */
@Entity
@Table(name = "investigador")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "investigador")
public class Investigador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido_1")
    private String apellido1;

    @Column(name = "apellido_2")
    private String apellido2;

    @Column(name = "esexterno")
    private Boolean esexterno;

    @ManyToOne
    private Genero genero;

    @ManyToOne
    private Grado grado;

    @ManyToOne
    private Departamento departamento;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Investigador nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public Investigador apellido1(String apellido1) {
        this.apellido1 = apellido1;
        return this;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public Investigador apellido2(String apellido2) {
        this.apellido2 = apellido2;
        return this;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public Boolean isEsexterno() {
        return esexterno;
    }

    public Investigador esexterno(Boolean esexterno) {
        this.esexterno = esexterno;
        return this;
    }

    public void setEsexterno(Boolean esexterno) {
        this.esexterno = esexterno;
    }

    public Genero getGenero() {
        return genero;
    }

    public Investigador genero(Genero genero) {
        this.genero = genero;
        return this;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Grado getGrado() {
        return grado;
    }

    public Investigador grado(Grado grado) {
        this.grado = grado;
        return this;
    }

    public void setGrado(Grado grado) {
        this.grado = grado;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public Investigador departamento(Departamento departamento) {
        this.departamento = departamento;
        return this;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
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
        Investigador investigador = (Investigador) o;
        if (investigador.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), investigador.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Investigador{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", apellido1='" + getApellido1() + "'" +
            ", apellido2='" + getApellido2() + "'" +
            ", esexterno='" + isEsexterno() + "'" +
            "}";
    }
}
