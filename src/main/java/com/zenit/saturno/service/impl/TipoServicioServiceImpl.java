package com.zenit.saturno.service.impl;

import com.zenit.saturno.service.TipoServicioService;
import com.zenit.saturno.domain.TipoServicio;
import com.zenit.saturno.repository.TipoServicioRepository;
import com.zenit.saturno.service.dto.TipoServicioDTO;
import com.zenit.saturno.service.mapper.TipoServicioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TipoServicio.
 */
@Service
@Transactional
public class TipoServicioServiceImpl implements TipoServicioService {

    private final Logger log = LoggerFactory.getLogger(TipoServicioServiceImpl.class);

    private final TipoServicioRepository tipoServicioRepository;

    private final TipoServicioMapper tipoServicioMapper;

    public TipoServicioServiceImpl(TipoServicioRepository tipoServicioRepository, TipoServicioMapper tipoServicioMapper) {
        this.tipoServicioRepository = tipoServicioRepository;
        this.tipoServicioMapper = tipoServicioMapper;
    }

    /**
     * Save a tipoServicio.
     *
     * @param tipoServicioDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TipoServicioDTO save(TipoServicioDTO tipoServicioDTO) {
        log.debug("Request to save TipoServicio : {}", tipoServicioDTO);

        TipoServicio tipoServicio = tipoServicioMapper.toEntity(tipoServicioDTO);
        tipoServicio = tipoServicioRepository.save(tipoServicio);
        return tipoServicioMapper.toDto(tipoServicio);
    }

    /**
     * Get all the tipoServicios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoServicioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoServicios");
        return tipoServicioRepository.findAll(pageable)
            .map(tipoServicioMapper::toDto);
    }


    /**
     * Get one tipoServicio by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TipoServicioDTO> findOne(Long id) {
        log.debug("Request to get TipoServicio : {}", id);
        return tipoServicioRepository.findById(id)
            .map(tipoServicioMapper::toDto);
    }

    /**
     * Delete the tipoServicio by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoServicio : {}", id);
        tipoServicioRepository.deleteById(id);
    }
}
