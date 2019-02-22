package com.zenit.saturno.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.zenit.saturno.domain.enumeration.Dia;

/**
 * A Horario.
 */
@Entity
@Table(name = "horario")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Horario implements Serializable {

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
    @Enumerated(EnumType.STRING)
    @Column(name = "dia", nullable = false)
    private Dia dia;

    @ManyToMany(mappedBy = "horarios")
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

    public Horario descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getHora() {
        return hora;
    }

    public Horario hora(Integer hora) {
        this.hora = hora;
        return this;
    }

    public void setHora(Integer hora) {
        this.hora = hora;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public Horario duracion(Integer duracion) {
        this.duracion = duracion;
        return this;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public Dia getDia() {
        return dia;
    }

    public Horario dia(Dia dia) {
        this.dia = dia;
        return this;
    }

    public void setDia(Dia dia) {
        this.dia = dia;
    }

    public Set<Agenda> getAgenda() {
        return agenda;
    }

    public Horario agenda(Set<Agenda> agenda) {
        this.agenda = agenda;
        return this;
    }

    public Horario addAgenda(Agenda agenda) {
        this.agenda.add(agenda);
        agenda.getHorarios().add(this);
        return this;
    }

    public Horario removeAgenda(Agenda agenda) {
        this.agenda.remove(agenda);
        agenda.getHorarios().remove(this);
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
        Horario horario = (Horario) o;
        if (horario.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), horario.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Horario{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", hora=" + getHora() +
            ", duracion=" + getDuracion() +
            ", dia='" + getDia() + "'" +
            "}";
    }
}