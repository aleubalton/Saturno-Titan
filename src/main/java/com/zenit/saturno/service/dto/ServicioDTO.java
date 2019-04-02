package com.zenit.saturno.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

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

    private Long tipoId;

    private String tipoCodigo;

    private String tipoNombre;

    private Set<TareaDTO> tareas = new HashSet<>();

    private Long planId;

    private String planNombre;

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

    public Long getTipoId() {
        return tipoId;
    }

    public void setTipoId(Long tipoServicioId) {
        this.tipoId = tipoServicioId;
    }

    public String getTipoCodigo() {
        return tipoCodigo;
    }

    public void setTipoCodigo(String tipoServicioCodigo) {
        this.tipoCodigo = tipoServicioCodigo;
    }

    public String getTipoNombre() {
        return tipoNombre;
    }

    public void setTipoNombre(String tipoNombre) {
        this.tipoNombre = tipoNombre;
    }

    public Set<TareaDTO> getTareas() {
        return tareas;
    }

    public void setTareas(Set<TareaDTO> tareas) {
        this.tareas = tareas;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planMantenimientoId) {
        this.planId = planMantenimientoId;
    }

    public String getPlanNombre() {
        return planNombre;
    }

    public void setPlanNombre(String planMantenimientoNombre) {
        this.planNombre = planMantenimientoNombre;
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
            ", tipo=" + getTipoId() +
            ", tipo='" + getTipoCodigo() + "'" +
            ", plan=" + getPlanId() +
            ", plan='" + getPlanNombre() + "'" +
            "}";
    }
}
