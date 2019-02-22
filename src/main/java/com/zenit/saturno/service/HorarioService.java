package com.zenit.saturno.service;

import com.zenit.saturno.service.dto.HorarioDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Horario.
 */
public interface HorarioService {

    /**
     * Save a horario.
     *
     * @param horarioDTO the entity to save
     * @return the persisted entity
     */
    HorarioDTO save(HorarioDTO horarioDTO);

    /**
     * Get all the horarios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<HorarioDTO> findAll(Pageable pageable);


    /**
     * Get the "id" horario.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<HorarioDTO> findOne(Long id);

    /**
     * Delete the "id" horario.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
