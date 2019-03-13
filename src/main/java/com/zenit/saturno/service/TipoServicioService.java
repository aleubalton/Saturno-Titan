package com.zenit.saturno.service;

import com.zenit.saturno.service.dto.TipoServicioDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TipoServicio.
 */
public interface TipoServicioService {

    /**
     * Save a tipoServicio.
     *
     * @param tipoServicioDTO the entity to save
     * @return the persisted entity
     */
    TipoServicioDTO save(TipoServicioDTO tipoServicioDTO);

    /**
     * Get all the tipoServicios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TipoServicioDTO> findAll(Pageable pageable);


    /**
     * Get the "id" tipoServicio.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TipoServicioDTO> findOne(Long id);

    /**
     * Delete the "id" tipoServicio.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
