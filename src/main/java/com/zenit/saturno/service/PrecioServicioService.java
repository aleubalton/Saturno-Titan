package com.zenit.saturno.service;

import com.zenit.saturno.service.dto.PrecioServicioDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing PrecioServicio.
 */
public interface PrecioServicioService {

    /**
     * Save a precioServicio.
     *
     * @param precioServicioDTO the entity to save
     * @return the persisted entity
     */
    PrecioServicioDTO save(PrecioServicioDTO precioServicioDTO);

    /**
     * Get all the precioServicios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PrecioServicioDTO> findAll(Pageable pageable);


    /**
     * Get the "id" precioServicio.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PrecioServicioDTO> findOne(Long id);

    /**
     * Delete the "id" precioServicio.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
