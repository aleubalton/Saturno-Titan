package com.zenit.saturno.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.zenit.saturno.domain.enumeration.Dia;

/**
 * A DTO for the Horario entity.
 */
public class HorarioDTO implements Serializable {

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
    private Dia dia;

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

    public Dia getDia() {
        return dia;
    }

    public void setDia(Dia dia) {
        this.dia = dia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HorarioDTO horarioDTO = (HorarioDTO) o;
        if (horarioDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), horarioDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HorarioDTO{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", hora=" + getHora() +
            ", duracion=" + getDuracion() +
            ", dia='" + getDia() + "'" +
            "}";
    }
}
