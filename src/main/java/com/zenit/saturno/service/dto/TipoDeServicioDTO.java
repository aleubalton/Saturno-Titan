package com.zenit.saturno.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.zenit.saturno.domain.enumeration.TipoRecurso;

/**
 * A DTO for the TipoDeServicio entity.
 */
public class TipoDeServicioDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String nombre;

    @NotNull
    @Size(min = 3, max = 4)
    private String codigo;

    private Boolean interno;

    private TipoRecurso tipoDeRecurso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Boolean isInterno() {
        return interno;
    }

    public void setInterno(Boolean interno) {
        this.interno = interno;
    }

    public TipoRecurso getTipoDeRecurso() {
        return tipoDeRecurso;
    }

    public void setTipoDeRecurso(TipoRecurso tipoDeRecurso) {
        this.tipoDeRecurso = tipoDeRecurso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TipoDeServicioDTO tipoDeServicioDTO = (TipoDeServicioDTO) o;
        if (tipoDeServicioDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipoDeServicioDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipoDeServicioDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", codigo='" + getCodigo() + "'" +
            ", interno='" + isInterno() + "'" +
            ", tipoDeRecurso='" + getTipoDeRecurso() + "'" +
            "}";
    }
}
