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

/**
 * A PlanMantenimiento.
 */
@Entity
@Table(name = "plan_mantenimiento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PlanMantenimiento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 3, max = 4)
    @Column(name = "codigo", length = 4, nullable = false)
    private String codigo;

    @NotNull
    @Size(max = 100)
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "plan")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Servicio> servicios = new HashSet<>();
    @OneToMany(mappedBy = "plan")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Modelo> modelos = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public PlanMantenimiento codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public PlanMantenimiento nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Servicio> getServicios() {
        return servicios;
    }

    public PlanMantenimiento servicios(Set<Servicio> servicios) {
        this.servicios = servicios;
        return this;
    }

    public PlanMantenimiento addServicio(Servicio servicio) {
        this.servicios.add(servicio);
        servicio.setPlan(this);
        return this;
    }

    public PlanMantenimiento removeServicio(Servicio servicio) {
        this.servicios.remove(servicio);
        servicio.setPlan(null);
        return this;
    }

    public void setServicios(Set<Servicio> servicios) {
        this.servicios = servicios;
    }

    public Set<Modelo> getModelos() {
        return modelos;
    }

    public PlanMantenimiento modelos(Set<Modelo> modelos) {
        this.modelos = modelos;
        return this;
    }

    public PlanMantenimiento addModelo(Modelo modelo) {
        this.modelos.add(modelo);
        modelo.setPlan(this);
        return this;
    }

    public PlanMantenimiento removeModelo(Modelo modelo) {
        this.modelos.remove(modelo);
        modelo.setPlan(null);
        return this;
    }

    public void setModelos(Set<Modelo> modelos) {
        this.modelos = modelos;
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
        PlanMantenimiento planMantenimiento = (PlanMantenimiento) o;
        if (planMantenimiento.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), planMantenimiento.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlanMantenimiento{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
