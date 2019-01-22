package com.zenit.saturno.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.zenit.saturno.domain.enumeration.TipoRecurso;

/**
 * A TipoDeServicio.
 */
@Entity
@Table(name = "tipo_de_servicio")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TipoDeServicio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @NotNull
    @Size(min = 3, max = 4)
    @Column(name = "codigo", length = 4, nullable = false)
    private String codigo;

    @Column(name = "interno")
    private Boolean interno;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_de_recurso")
    private TipoRecurso tipoDeRecurso;

    @OneToMany(mappedBy = "tipo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Servicio> servicios = new HashSet<>();
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

    public TipoDeServicio nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public TipoDeServicio codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Boolean isInterno() {
        return interno;
    }

    public TipoDeServicio interno(Boolean interno) {
        this.interno = interno;
        return this;
    }

    public void setInterno(Boolean interno) {
        this.interno = interno;
    }

    public TipoRecurso getTipoDeRecurso() {
        return tipoDeRecurso;
    }

    public TipoDeServicio tipoDeRecurso(TipoRecurso tipoDeRecurso) {
        this.tipoDeRecurso = tipoDeRecurso;
        return this;
    }

    public void setTipoDeRecurso(TipoRecurso tipoDeRecurso) {
        this.tipoDeRecurso = tipoDeRecurso;
    }

    public Set<Servicio> getServicios() {
        return servicios;
    }

    public TipoDeServicio servicios(Set<Servicio> servicios) {
        this.servicios = servicios;
        return this;
    }

    public TipoDeServicio addServicio(Servicio servicio) {
        this.servicios.add(servicio);
        servicio.setTipo(this);
        return this;
    }

    public TipoDeServicio removeServicio(Servicio servicio) {
        this.servicios.remove(servicio);
        servicio.setTipo(null);
        return this;
    }

    public void setServicios(Set<Servicio> servicios) {
        this.servicios = servicios;
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
        TipoDeServicio tipoDeServicio = (TipoDeServicio) o;
        if (tipoDeServicio.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipoDeServicio.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipoDeServicio{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", codigo='" + getCodigo() + "'" +
            ", interno='" + isInterno() + "'" +
            ", tipoDeRecurso='" + getTipoDeRecurso() + "'" +
            "}";
    }
}
