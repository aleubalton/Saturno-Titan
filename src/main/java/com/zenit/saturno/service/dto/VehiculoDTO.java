package com.zenit.saturno.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Vehiculo entity.
 */
public class VehiculoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 6, max = 7)
    @Pattern(regexp = "^[A-Z]{2}\\d{3}[A-Z]{2}|[A-Z]{3}[0-9]{3}$")
    private String patente;

    @NotNull
    @Min(value = 1950)
    private Integer anio;

    @NotNull
    @Min(value = 0)
    private Integer kilometraje;

    @Size(min = 17, max = 18)
    private String vin;

    @Size(min = 17, max = 18)
    private String motor;

    private Long modeloId;

    private String modeloNombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(Integer kilometraje) {
        this.kilometraje = kilometraje;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VehiculoDTO vehiculoDTO = (VehiculoDTO) o;
        if (vehiculoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehiculoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VehiculoDTO{" +
            "id=" + getId() +
            ", patente='" + getPatente() + "'" +
            ", anio=" + getAnio() +
            ", kilometraje=" + getKilometraje() +
            ", vin='" + getVin() + "'" +
            ", motor='" + getMotor() + "'" +
            ", modelo=" + getModeloId() +
            ", modelo='" + getModeloNombre() + "'" +
            "}";
    }
}
