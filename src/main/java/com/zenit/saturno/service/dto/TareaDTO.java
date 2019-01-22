package com.zenit.saturno.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.zenit.saturno.domain.enumeration.TipoTarea;

/**
 * A DTO for the Tarea entity.
 */
public class TareaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 4)
    private String codigo;

    @NotNull
    @Size(max = 100)
    private String descripcion;

    @NotNull
    private TipoTarea tipo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoTarea getTipo() {
        return tipo;
    }

    public void setTipo(TipoTarea tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TareaDTO tareaDTO = (TareaDTO) o;
        if (tareaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tareaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TareaDTO{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", tipo='" + getTipo() + "'" +
            "}";
    }
}
