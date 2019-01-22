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

import com.zenit.saturno.domain.enumeration.TipoRecurso;

/**
 * A Agenda.
 */
@Entity
@Table(name = "agenda")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Agenda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_de_recurso", nullable = false)
    private TipoRecurso tipoDeRecurso;

    @Column(name = "fecha_desde")
    private LocalDate fechaDesde;

    @Column(name = "fecha_hasta")
    private LocalDate fechaHasta;

    @Column(name = "activa")
    private Boolean activa;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "agenda_intervalo",
               joinColumns = @JoinColumn(name = "agenda_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "intervalos_id", referencedColumnName = "id"))
    private Set<Intervalo> intervalos = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "agenda_dia_no_laborable",
               joinColumns = @JoinColumn(name = "agenda_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "dia_no_laborables_id", referencedColumnName = "id"))
    private Set<DiaNoLaborable> diaNoLaborables = new HashSet<>();

    @OneToMany(mappedBy = "agenda")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Turno> turnos = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Agenda nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoRecurso getTipoDeRecurso() {
        return tipoDeRecurso;
    }

    public Agenda tipoDeRecurso(TipoRecurso tipoDeRecurso) {
        this.tipoDeRecurso = tipoDeRecurso;
        return this;
    }

    public void setTipoDeRecurso(TipoRecurso tipoDeRecurso) {
        this.tipoDeRecurso = tipoDeRecurso;
    }

    public LocalDate getFechaDesde() {
        return fechaDesde;
    }

    public Agenda fechaDesde(LocalDate fechaDesde) {
        this.fechaDesde = fechaDesde;
        return this;
    }

    public void setFechaDesde(LocalDate fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public LocalDate getFechaHasta() {
        return fechaHasta;
    }

    public Agenda fechaHasta(LocalDate fechaHasta) {
        this.fechaHasta = fechaHasta;
        return this;
    }

    public void setFechaHasta(LocalDate fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public Boolean isActiva() {
        return activa;
    }

    public Agenda activa(Boolean activa) {
        this.activa = activa;
        return this;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    public Set<Intervalo> getIntervalos() {
        return intervalos;
    }

    public Agenda intervalos(Set<Intervalo> intervalos) {
        this.intervalos = intervalos;
        return this;
    }

    public Agenda addIntervalo(Intervalo intervalo) {
        this.intervalos.add(intervalo);
        intervalo.getAgenda().add(this);
        return this;
    }

    public Agenda removeIntervalo(Intervalo intervalo) {
        this.intervalos.remove(intervalo);
        intervalo.getAgenda().remove(this);
        return this;
    }

    public void setIntervalos(Set<Intervalo> intervalos) {
        this.intervalos = intervalos;
    }

    public Set<DiaNoLaborable> getDiaNoLaborables() {
        return diaNoLaborables;
    }

    public Agenda diaNoLaborables(Set<DiaNoLaborable> diaNoLaborables) {
        this.diaNoLaborables = diaNoLaborables;
        return this;
    }

    public Agenda addDiaNoLaborable(DiaNoLaborable diaNoLaborable) {
        this.diaNoLaborables.add(diaNoLaborable);
        diaNoLaborable.getAgenda().add(this);
        return this;
    }

    public Agenda removeDiaNoLaborable(DiaNoLaborable diaNoLaborable) {
        this.diaNoLaborables.remove(diaNoLaborable);
        diaNoLaborable.getAgenda().remove(this);
        return this;
    }

    public void setDiaNoLaborables(Set<DiaNoLaborable> diaNoLaborables) {
        this.diaNoLaborables = diaNoLaborables;
    }

    public Set<Turno> getTurnos() {
        return turnos;
    }

    public Agenda turnos(Set<Turno> turnos) {
        this.turnos = turnos;
        return this;
    }

    public Agenda addTurno(Turno turno) {
        this.turnos.add(turno);
        turno.setAgenda(this);
        return this;
    }

    public Agenda removeTurno(Turno turno) {
        this.turnos.remove(turno);
        turno.setAgenda(null);
        return this;
    }

    public void setTurnos(Set<Turno> turnos) {
        this.turnos = turnos;
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
        Agenda agenda = (Agenda) o;
        if (agenda.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), agenda.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Agenda{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", tipoDeRecurso='" + getTipoDeRecurso() + "'" +
            ", fechaDesde='" + getFechaDesde() + "'" +
            ", fechaHasta='" + getFechaHasta() + "'" +
            ", activa='" + isActiva() + "'" +
            "}";
    }
}
