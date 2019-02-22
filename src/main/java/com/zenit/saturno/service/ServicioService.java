package com.zenit.saturno.service;

import com.zenit.saturno.service.dto.ServicioDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Servicio.
 */
public interface ServicioService {

    /**
     * Save a servicio.
     *
     * @param servicioDTO the entity to save
     * @return the persisted entity
     */
    ServicioDTO save(ServicioDTO servicioDTO);

    /**
     * Get all the servicios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ServicioDTO> findAll(Pageable pageable);

    /**
     * Get all the Servicio with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<ServicioDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" servicio.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ServicioDTO> findOne(Long id);

    /**
     * Delete the "id" servicio.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
