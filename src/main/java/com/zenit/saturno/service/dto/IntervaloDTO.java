package com.zenit.saturno.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.zenit.saturno.domain.enumeration.Dia;

/**
 * A DTO for the Intervalo entity.
 */
public class IntervaloDTO implements Serializable {

    private Long id;

    private Instant fechaHoraDesde;

    @Min(value = 0)
    @Max(value = 600)
    private Integer duracion;

    private Dia dia;

    private Boolean repite;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFechaHoraDesde() {
        return fechaHoraDesde;
    }

    public void setFechaHoraDesde(Instant fechaHoraDesde) {
        this.fechaHoraDesde = fechaHoraDesde;
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

    public Boolean isRepite() {
        return repite;
    }

    public void setRepite(Boolean repite) {
        this.repite = repite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IntervaloDTO intervaloDTO = (IntervaloDTO) o;
        if (intervaloDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), intervaloDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IntervaloDTO{" +
            "id=" + getId() +
            ", fechaHoraDesde='" + getFechaHoraDesde() + "'" +
            ", duracion=" + getDuracion() +
            ", dia='" + getDia() + "'" +
            ", repite='" + isRepite() + "'" +
            "}";
    }
}
