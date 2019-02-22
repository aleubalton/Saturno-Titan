package com.zenit.saturno.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the HorarioEspecial entity.
 */
public class HorarioEspecialDTO implements Serializable {

    private Long id;

    @NotNull
    private String descripcion;

    @NotNull
    @Min(value = 0)
    @Max(value = 23)
    private Integer hora;

    @NotNull
    @Min(value = 0)
    @Max(value = 600)
    private Integer duracion;

    @NotNull
    private LocalDate fecha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getHora() {
        return hora;
    }

    public void setHora(Integer hora) {
        this.hora = hora;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HorarioEspecialDTO horarioEspecialDTO = (HorarioEspecialDTO) o;
        if (horarioEspecialDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), horarioEspecialDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HorarioEspecialDTO{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", hora=" + getHora() +
            ", duracion=" + getDuracion() +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
