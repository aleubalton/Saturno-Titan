package com.zenit.saturno.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A HorarioEspecial.
 */
@Entity
@Table(name = "horario_especial")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HorarioEspecial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @NotNull
    @Min(value = 0)
    @Max(value = 23)
    @Column(name = "hora", nullable = false)
    private Integer hora;

    @NotNull
    @Min(value = 0)
    @Max(value = 600)
    @Column(name = "duracion", nullable = false)
    private Integer duracion;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @ManyToMany(mappedBy = "horarioEspecials")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Agenda> agenda = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public HorarioEspecial descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getHora() {
        return hora;
    }

    public HorarioEspecial hora(Integer hora) {
        this.hora = hora;
        return this;
    }

    public void setHora(Integer hora) {
        this.hora = hora;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public HorarioEspecial duracion(Integer duracion) {
        this.duracion = duracion;
        return this;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public HorarioEspecial fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Set<Agenda> getAgenda() {
        return agenda;
    }

    public HorarioEspecial agenda(Set<Agenda> agenda) {
        this.agenda = agenda;
        return this;
    }

    public HorarioEspecial addAgenda(Agenda agenda) {
        this.agenda.add(agenda);
        agenda.getHorarioEspecials().add(this);
        return this;
    }

    public HorarioEspecial removeAgenda(Agenda agenda) {
        this.agenda.remove(agenda);
        agenda.getHorarioEspecials().remove(this);
        return this;
    }

    public void setAgenda(Set<Agenda> agenda) {
        this.agenda = agenda;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HorarioEspecial horarioEspecial = (HorarioEspecial) o;
        if (horarioEspecial.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), horarioEspecial.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HorarioEspecial{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", hora=" + getHora() +
            ", duracion=" + getDuracion() +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
