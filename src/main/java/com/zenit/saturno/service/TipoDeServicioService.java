package com.zenit.saturno.service;

import com.zenit.saturno.service.dto.TipoDeServicioDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TipoDeServicio.
 */
public interface TipoDeServicioService {

    /**
     * Save a tipoDeServicio.
     *
     * @param tipoDeServicioDTO the entity to save
     * @return the persisted entity
     */
    TipoDeServicioDTO save(TipoDeServicioDTO tipoDeServicioDTO);

    /**
     * Get all the tipoDeServicios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TipoDeServicioDTO> findAll(Pageable pageable);


    /**
     * Get the "id" tipoDeServicio.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TipoDeServicioDTO> findOne(Long id);

    /**
     * Delete the "id" tipoDeServicio.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
