package com.zenit.saturno.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PrecioServicio.
 */
@Entity
@Table(name = "precio_servicio")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PrecioServicio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Min(value = 0)
    @Column(name = "precio", nullable = false)
    private Integer precio;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("precios")
    private Modelo modelo;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("precios")
    private Servicio servicio;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPrecio() {
        return precio;
    }

    public PrecioServicio precio(Integer precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public PrecioServicio modelo(Modelo modelo) {
        this.modelo = modelo;
        return this;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public PrecioServicio servicio(Servicio servicio) {
        this.servicio = servicio;
        return this;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
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
        PrecioServicio precioServicio = (PrecioServicio) o;
        if (precioServicio.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), precioServicio.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PrecioServicio{" +
            "id=" + getId() +
            ", precio=" + getPrecio() +
            "}";
    }
}
