package com.zenit.saturno.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.zenit.saturno.domain.enumeration.Estado;

/**
 * A Turno.
 */
@Entity
@Table(name = "turno")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Turno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "fecha_hora", nullable = false)
    private Instant fechaHora;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("turnos")
    private Agenda agenda;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("turnos")
    private Vehiculo vehiculo;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "turno_servicio",
               joinColumns = @JoinColumn(name = "turnos_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "servicios_id", referencedColumnName = "id"))
    private Set<Servicio> servicios = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("turnos")
    private Cliente cliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFechaHora() {
        return fechaHora;
    }

    public Turno fechaHora(Instant fechaHora) {
        this.fechaHora = fechaHora;
        return this;
    }

    public void setFechaHora(Instant fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Estado getEstado() {
        return estado;
    }

    public Turno estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public Turno agenda(Agenda agenda) {
        this.agenda = agenda;
        return this;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public Turno vehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
        return this;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Set<Servicio> getServicios() {
        return servicios;
    }

    public Turno servicios(Set<Servicio> servicios) {
        this.servicios = servicios;
        return this;
    }

    public Turno addServicio(Servicio servicio) {
        this.servicios.add(servicio);
        servicio.getTurnos().add(this);
        return this;
    }

    public Turno removeServicio(Servicio servicio) {
        this.servicios.remove(servicio);
        servicio.getTurnos().remove(this);
        return this;
    }

    public void setServicios(Set<Servicio> servicios) {
        this.servicios = servicios;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Turno cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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
        Turno turno = (Turno) o;
        if (turno.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), turno.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Turno{" +
            "id=" + getId() +
            ", fechaHora='" + getFechaHora() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
