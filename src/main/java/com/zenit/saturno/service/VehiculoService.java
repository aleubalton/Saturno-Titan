package com.zenit.saturno.service;

import com.zenit.saturno.service.dto.VehiculoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Vehiculo.
 */
public interface VehiculoService {

    /**
     * Save a vehiculo.
     *
     * @param vehiculoDTO the entity to save
     * @return the persisted entity
     */
    VehiculoDTO save(VehiculoDTO vehiculoDTO);

    /**
     * Get all the vehiculos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VehiculoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" vehiculo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<VehiculoDTO> findOne(Long id);

    /**
     * Get the "patente" vehiculo.
     *
     * @param patente the patente of the entity
     * @return the entity
     */
    Optional<VehiculoDTO> findOneByPatente(String patente);

    /**
     * Delete the "id" vehiculo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
