package com.zenit.saturno.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Vehiculo.
 */
@Entity
@Table(name = "vehiculo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Vehiculo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 6, max = 7)
    @Pattern(regexp = "^[A-Z]{2}\\d{3}[A-Z]{2}|[A-Z]{3}[0-9]{3}$")
    @Column(name = "patente", length = 7, nullable = false)
    private String patente;

    @NotNull
    @Min(value = 1950)
    @Max(value = 2018)
    @Column(name = "anio", nullable = false)
    private Integer anio;

    @NotNull
    @Column(name = "kilometraje", nullable = false)
    private Integer kilometraje;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("vehiculos")
    private Modelo modelo;

    @OneToMany(mappedBy = "vehiculo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Turno> turnos = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatente() {
        return patente;
    }

    public Vehiculo patente(String patente) {
        this.patente = patente;
        return this;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public Integer getAnio() {
        return anio;
    }

    public Vehiculo anio(Integer anio) {
        this.anio = anio;
        return this;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getKilometraje() {
        return kilometraje;
    }

    public Vehiculo kilometraje(Integer kilometraje) {
        this.kilometraje = kilometraje;
        return this;
    }

    public void setKilometraje(Integer kilometraje) {
        this.kilometraje = kilometraje;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public Vehiculo modelo(Modelo modelo) {
        this.modelo = modelo;
        return this;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public Set<Turno> getTurnos() {
        return turnos;
    }

    public Vehiculo turnos(Set<Turno> turnos) {
        this.turnos = turnos;
        return this;
    }

    public Vehiculo addTurno(Turno turno) {
        this.turnos.add(turno);
        turno.setVehiculo(this);
        return this;
    }

    public Vehiculo removeTurno(Turno turno) {
        this.turnos.remove(turno);
        turno.setVehiculo(null);
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
        Vehiculo vehiculo = (Vehiculo) o;
        if (vehiculo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehiculo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Vehiculo{" +
            "id=" + getId() +
            ", patente='" + getPatente() + "'" +
            ", anio=" + getAnio() +
            ", kilometraje=" + getKilometraje() +
            "}";
    }
}
