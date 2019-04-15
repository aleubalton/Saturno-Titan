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
    @Column(name = "codigo_reserva", nullable = false)
    private String codigoReserva;

    @NotNull
    @Column(name = "fecha_hora", nullable = false)
    private Instant fechaHora;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "creation_date", nullable = false, updatable = false)
    private Instant creationDate = Instant.now();

    @NotNull
    @Min(value = 0)
    @Max(value = 600)
    @Column(name = "duracion", nullable = false)
    private Integer duracion;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "costo", nullable = false)
    private Float costo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "indicaciones")
    private String indicaciones;

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

    public String getCodigoReserva() {
        return codigoReserva;
    }

    public Turno codigoReserva(String codigoReserva) {
        this.codigoReserva = codigoReserva;
        return this;
    }

    public void setCodigoReserva(String codigoReserva) {
        this.codigoReserva = codigoReserva;
    }

    public Instant getFechaHora() {
        return fechaHora;
    }

    public Turno fechaHora(Instant fechaHora) {
        this.fechaHora = fechaHora;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Turno creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setFechaHora(Instant fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public Turno duracion(Integer duracion) {
        this.duracion = duracion;
        return this;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public Float getCosto() {
        return costo;
    }

    public Turno costo(Float costo) {
        this.costo = costo;
        return this;
    }

    public void setCosto(Float costo) {
        this.costo = costo;
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

    public String getComentario() {
        return comentario;
    }

    public Turno comentario(String comentario) {
        this.comentario = comentario;
        return this;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public Turno indicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
        return this;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
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
            ", codigoReserva='" + getCodigoReserva() + "'" +
            ", fechaHora='" + getFechaHora() + "'" +
            ", duracion=" + getDuracion() +
            ", costo=" + getCosto() +
            ", estado='" + getEstado() + "'" +
            ", comentario='" + getComentario() + "'" +
            ", indicaciones='" + getIndicaciones() + "'" +
            "}";
    }
}
