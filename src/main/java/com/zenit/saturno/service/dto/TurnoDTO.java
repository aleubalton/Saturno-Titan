package com.zenit.saturno.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.zenit.saturno.domain.enumeration.Estado;

/**
 * A DTO for the Turno entity.
 */
public class TurnoDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant fechaHora;

    @NotNull
    private Estado estado;

    private Long agendaId;

    private String agendaNombre;

    private Long vehiculoId;

    private String vehiculoPatente;

    private Set<ServicioDTO> servicios = new HashSet<>();

    private Long clienteId;

    private String clienteApellido;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Instant fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Long getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(Long agendaId) {
        this.agendaId = agendaId;
    }

    public String getAgendaNombre() {
        return agendaNombre;
    }

    public void setAgendaNombre(String agendaNombre) {
        this.agendaNombre = agendaNombre;
    }

    public Long getVehiculoId() {
        return vehiculoId;
    }

    public void setVehiculoId(Long vehiculoId) {
        this.vehiculoId = vehiculoId;
    }

    public String getVehiculoPatente() {
        return vehiculoPatente;
    }

    public void setVehiculoPatente(String vehiculoPatente) {
        this.vehiculoPatente = vehiculoPatente;
    }

    public Set<ServicioDTO> getServicios() {
        return servicios;
    }

    public void setServicios(Set<ServicioDTO> servicios) {
        this.servicios = servicios;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteApellido() {
        return clienteApellido;
    }

    public void setClienteApellido(String clienteApellido) {
        this.clienteApellido = clienteApellido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TurnoDTO turnoDTO = (TurnoDTO) o;
        if (turnoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), turnoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TurnoDTO{" +
            "id=" + getId() +
            ", fechaHora='" + getFechaHora() + "'" +
            ", estado='" + getEstado() + "'" +
            ", agenda=" + getAgendaId() +
            ", agenda='" + getAgendaNombre() + "'" +
            ", vehiculo=" + getVehiculoId() +
            ", vehiculo='" + getVehiculoPatente() + "'" +
            ", cliente=" + getClienteId() +
            ", cliente='" + getClienteApellido() + "'" +
            "}";
    }
}
