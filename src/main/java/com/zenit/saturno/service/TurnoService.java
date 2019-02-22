package com.zenit.saturno.service;

import com.zenit.saturno.service.dto.TurnoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Turno.
 */
public interface TurnoService {

    /**
     * Save a turno.
     *
     * @param turnoDTO the entity to save
     * @return the persisted entity
     */
    TurnoDTO save(TurnoDTO turnoDTO);

    /**
     * Get all the turnos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TurnoDTO> findAll(Pageable pageable);

    /**
     * Get all the Turno with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<TurnoDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" turno.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TurnoDTO> findOne(Long id);

    /**
     * Delete the "id" turno.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
