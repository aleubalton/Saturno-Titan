package com.zenit.saturno.service;

import com.zenit.saturno.service.dto.DiaNoLaborableDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing DiaNoLaborable.
 */
public interface DiaNoLaborableService {

    /**
     * Save a diaNoLaborable.
     *
     * @param diaNoLaborableDTO the entity to save
     * @return the persisted entity
     */
    DiaNoLaborableDTO save(DiaNoLaborableDTO diaNoLaborableDTO);

    /**
     * Get all the diaNoLaborables.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DiaNoLaborableDTO> findAll(Pageable pageable);


    /**
     * Get the "id" diaNoLaborable.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DiaNoLaborableDTO> findOne(Long id);

    /**
     * Delete the "id" diaNoLaborable.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
