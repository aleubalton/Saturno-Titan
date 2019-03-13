package com.zenit.saturno.service;

import com.zenit.saturno.service.dto.PlanMantenimientoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing PlanMantenimiento.
 */
public interface PlanMantenimientoService {

    /**
     * Save a planMantenimiento.
     *
     * @param planMantenimientoDTO the entity to save
     * @return the persisted entity
     */
    PlanMantenimientoDTO save(PlanMantenimientoDTO planMantenimientoDTO);

    /**
     * Get all the planMantenimientos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PlanMantenimientoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" planMantenimiento.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PlanMantenimientoDTO> findOne(Long id);

    /**
     * Delete the "id" planMantenimiento.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
