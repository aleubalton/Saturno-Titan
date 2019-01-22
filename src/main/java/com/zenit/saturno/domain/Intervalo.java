package com.zenit.saturno.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.zenit.saturno.domain.enumeration.Dia;

/**
 * A Intervalo.
 */
@Entity
@Table(name = "intervalo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Intervalo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "fecha_hora_desde")
    private Instant fechaHoraDesde;

    @Min(value = 0)
    @Max(value = 600)
    @Column(name = "duracion")
    private Integer duracion;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia")
    private Dia dia;

    @Column(name = "repite")
    private Boolean repite;

    @ManyToMany(mappedBy = "intervalos")
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

    public Instant getFechaHoraDesde() {
        return fechaHoraDesde;
    }

    public Intervalo fechaHoraDesde(Instant fechaHoraDesde) {
        this.fechaHoraDesde = fechaHoraDesde;
        return this;
    }

    public void setFechaHoraDesde(Instant fechaHoraDesde) {
        this.fechaHoraDesde = fechaHoraDesde;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public Intervalo duracion(Integer duracion) {
        this.duracion = duracion;
        return this;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public Dia getDia() {
        return dia;
    }

    public Intervalo dia(Dia dia) {
        this.dia = dia;
        return this;
    }

    public void setDia(Dia dia) {
        this.dia = dia;
    }

    public Boolean isRepite() {
        return repite;
    }

    public Intervalo repite(Boolean repite) {
        this.repite = repite;
        return this;
    }

    public void setRepite(Boolean repite) {
        this.repite = repite;
    }

    public Set<Agenda> getAgenda() {
        return agenda;
    }

    public Intervalo agenda(Set<Agenda> agenda) {
        this.agenda = agenda;
        return this;
    }

    public Intervalo addAgenda(Agenda agenda) {
        this.agenda.add(agenda);
        agenda.getIntervalos().add(this);
        return this;
    }

    public Intervalo removeAgenda(Agenda agenda) {
        this.agenda.remove(agenda);
        agenda.getIntervalos().remove(this);
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
        Intervalo intervalo = (Intervalo) o;
        if (intervalo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), intervalo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Intervalo{" +
            "id=" + getId() +
            ", fechaHoraDesde='" + getFechaHoraDesde() + "'" +
            ", duracion=" + getDuracion() +
            ", dia='" + getDia() + "'" +
            ", repite='" + isRepite() + "'" +
            "}";
    }
}
