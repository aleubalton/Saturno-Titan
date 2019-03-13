package com.zenit.saturno.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.zenit.saturno.domain.enumeration.TipoRecurso;

/**
 * A DTO for the TipoServicio entity.
 */
public class TipoServicioDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String nombre;

    @NotNull
    @Size(min = 3, max = 4)
    private String codigo;

    @NotNull
    private Boolean interno;

    @NotNull
    private Boolean adicional;

    @NotNull
    private TipoRecurso tipoRecurso;

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

    public Boolean isAdicional() {
        return adicional;
    }

    public void setAdicional(Boolean adicional) {
        this.adicional = adicional;
    }

    public TipoRecurso getTipoRecurso() {
        return tipoRecurso;
    }

    public void setTipoRecurso(TipoRecurso tipoRecurso) {
        this.tipoRecurso = tipoRecurso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TipoServicioDTO tipoServicioDTO = (TipoServicioDTO) o;
        if (tipoServicioDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipoServicioDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipoServicioDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", codigo='" + getCodigo() + "'" +
            ", interno='" + isInterno() + "'" +
            ", adicional='" + isAdicional() + "'" +
            ", tipoRecurso='" + getTipoRecurso() + "'" +
            "}";
    }
}
