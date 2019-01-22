package com.zenit.saturno.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.zenit.saturno.domain.enumeration.Categoria;

/**
 * A DTO for the Servicio entity.
 */
public class ServicioDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String nombre;

    @NotNull
    @Size(min = 3, max = 4)
    private String codigo;

    @NotNull
    @Min(value = 0)
    private Integer kilometraje;

    @NotNull
    @Min(value = 0)
    @Max(value = 600)
    private Integer duracion;

    @NotNull
    private Categoria categoria;

    private Long tipoId;

    private String tipoCodigo;

    private Set<TareaDTO> tareas = new HashSet<>();

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

    public Integer getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(Integer kilometraje) {
        this.kilometraje = kilometraje;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Long getTipoId() {
        return tipoId;
    }

    public void setTipoId(Long tipoDeServicioId) {
        this.tipoId = tipoDeServicioId;
    }

    public String getTipoCodigo() {
        return tipoCodigo;
    }

    public void setTipoCodigo(String tipoDeServicioCodigo) {
        this.tipoCodigo = tipoDeServicioCodigo;
    }

    public Set<TareaDTO> getTareas() {
        return tareas;
    }

    public void setTareas(Set<TareaDTO> tareas) {
        this.tareas = tareas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServicioDTO servicioDTO = (ServicioDTO) o;
        if (servicioDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), servicioDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServicioDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", codigo='" + getCodigo() + "'" +
            ", kilometraje=" + getKilometraje() +
            ", duracion=" + getDuracion() +
            ", categoria='" + getCategoria() + "'" +
            ", tipo=" + getTipoId() +
            ", tipo='" + getTipoCodigo() + "'" +
            "}";
    }
}
