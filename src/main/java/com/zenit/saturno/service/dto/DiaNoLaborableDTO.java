package com.zenit.saturno.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import com.zenit.saturno.domain.enumeration.Dia;

/**
 * A DTO for the DiaNoLaborable entity.
 */
public class DiaNoLaborableDTO implements Serializable {

    private Long id;

    private LocalDate fecha;

    private Dia dia;

    private Boolean repite;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
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

        DiaNoLaborableDTO diaNoLaborableDTO = (DiaNoLaborableDTO) o;
        if (diaNoLaborableDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), diaNoLaborableDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DiaNoLaborableDTO{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", dia='" + getDia() + "'" +
            ", repite='" + isRepite() + "'" +
            "}";
    }
}
