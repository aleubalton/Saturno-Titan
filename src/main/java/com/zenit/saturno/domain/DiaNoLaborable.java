package com.zenit.saturno.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.zenit.saturno.domain.enumeration.Dia;

/**
 * A DiaNoLaborable.
 */
@Entity
@Table(name = "dia_no_laborable")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DiaNoLaborable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia")
    private Dia dia;

    @Column(name = "repite")
    private Boolean repite;

    @ManyToMany(mappedBy = "diaNoLaborables")
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

    public LocalDate getFecha() {
        return fecha;
    }

    public DiaNoLaborable fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Dia getDia() {
        return dia;
    }

    public DiaNoLaborable dia(Dia dia) {
        this.dia = dia;
        return this;
    }

    public void setDia(Dia dia) {
        this.dia = dia;
    }

    public Boolean isRepite() {
        return repite;
    }

    public DiaNoLaborable repite(Boolean repite) {
        this.repite = repite;
        return this;
    }

    public void setRepite(Boolean repite) {
        this.repite = repite;
    }

    public Set<Agenda> getAgenda() {
        return agenda;
    }

    public DiaNoLaborable agenda(Set<Agenda> agenda) {
        this.agenda = agenda;
        return this;
    }

    public DiaNoLaborable addAgenda(Agenda agenda) {
        this.agenda.add(agenda);
        agenda.getDiaNoLaborables().add(this);
        return this;
    }

    public DiaNoLaborable removeAgenda(Agenda agenda) {
        this.agenda.remove(agenda);
        agenda.getDiaNoLaborables().remove(this);
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
        DiaNoLaborable diaNoLaborable = (DiaNoLaborable) o;
        if (diaNoLaborable.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), diaNoLaborable.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DiaNoLaborable{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", dia='" + getDia() + "'" +
            ", repite='" + isRepite() + "'" +
            "}";
    }
}
