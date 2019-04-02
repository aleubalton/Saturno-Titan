package com.zenit.saturno.service;

import com.zenit.saturno.service.dto.HorarioEspecialDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing HorarioEspecial.
 */
public interface HorarioEspecialService {

    /**
     * Save a horarioEspecial.
     *
     * @param horarioEspecialDTO the entity to save
     * @return the persisted entity
     */
    HorarioEspecialDTO save(HorarioEspecialDTO horarioEspecialDTO);

    /**
     * Get all the horarioEspecials.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<HorarioEspecialDTO> findAll(Pageable pageable);


    /**
     * Get the "id" horarioEspecial.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<HorarioEspecialDTO> findOne(Long id);

    /**
     * Delete the "id" horarioEspecial.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
