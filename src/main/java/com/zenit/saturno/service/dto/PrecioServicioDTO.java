package com.zenit.saturno.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the PrecioServicio entity.
 */
public class PrecioServicioDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 0)
    private Integer precio;

    private Long modeloId;

    private String modeloNombre;

    private Long servicioId;

    private String servicioNombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public Long getModeloId() {
        return modeloId;
    }

    public void setModeloId(Long modeloId) {
        this.modeloId = modeloId;
    }

    public String getModeloNombre() {
        return modeloNombre;
    }

    public void setModeloNombre(String modeloNombre) {
        this.modeloNombre = modeloNombre;
    }

    public Long getServicioId() {
        return servicioId;
    }

    public void setServicioId(Long servicioId) {
        this.servicioId = servicioId;
    }

    public String getServicioNombre() {
        return servicioNombre;
    }

    public void setServicioNombre(String servicioNombre) {
        this.servicioNombre = servicioNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PrecioServicioDTO precioServicioDTO = (PrecioServicioDTO) o;
        if (precioServicioDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), precioServicioDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PrecioServicioDTO{" +
            "id=" + getId() +
            ", precio=" + getPrecio() +
            ", modelo=" + getModeloId() +
            ", modelo='" + getModeloNombre() + "'" +
            ", servicio=" + getServicioId() +
            ", servicio='" + getServicioNombre() + "'" +
            "}";
    }
}
