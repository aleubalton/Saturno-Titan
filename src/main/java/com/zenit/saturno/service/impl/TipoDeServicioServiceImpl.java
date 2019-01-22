package com.zenit.saturno.service.impl;

import com.zenit.saturno.service.TipoDeServicioService;
import com.zenit.saturno.domain.TipoDeServicio;
import com.zenit.saturno.repository.TipoDeServicioRepository;
import com.zenit.saturno.service.dto.TipoDeServicioDTO;
import com.zenit.saturno.service.mapper.TipoDeServicioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TipoDeServicio.
 */
@Service
@Transactional
public class TipoDeServicioServiceImpl implements TipoDeServicioService {

    private final Logger log = LoggerFactory.getLogger(TipoDeServicioServiceImpl.class);

    private final TipoDeServicioRepository tipoDeServicioRepository;

    private final TipoDeServicioMapper tipoDeServicioMapper;

    public TipoDeServicioServiceImpl(TipoDeServicioRepository tipoDeServicioRepository, TipoDeServicioMapper tipoDeServicioMapper) {
        this.tipoDeServicioRepository = tipoDeServicioRepository;
        this.tipoDeServicioMapper = tipoDeServicioMapper;
    }

    /**
     * Save a tipoDeServicio.
     *
     * @param tipoDeServicioDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TipoDeServicioDTO save(TipoDeServicioDTO tipoDeServicioDTO) {
        log.debug("Request to save TipoDeServicio : {}", tipoDeServicioDTO);

        TipoDeServicio tipoDeServicio = tipoDeServicioMapper.toEntity(tipoDeServicioDTO);
        tipoDeServicio = tipoDeServicioRepository.save(tipoDeServicio);
        return tipoDeServicioMapper.toDto(tipoDeServicio);
    }

    /**
     * Get all the tipoDeServicios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoDeServicioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoDeServicios");
        return tipoDeServicioRepository.findAll(pageable)
            .map(tipoDeServicioMapper::toDto);
    }


    /**
     * Get one tipoDeServicio by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TipoDeServicioDTO> findOne(Long id) {
        log.debug("Request to get TipoDeServicio : {}", id);
        return tipoDeServicioRepository.findById(id)
            .map(tipoDeServicioMapper::toDto);
    }

    /**
     * Delete the tipoDeServicio by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoDeServicio : {}", id);
        tipoDeServicioRepository.deleteById(id);
    }
}
