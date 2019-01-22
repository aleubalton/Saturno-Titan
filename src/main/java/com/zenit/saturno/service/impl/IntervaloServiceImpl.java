package com.zenit.saturno.service.impl;

import com.zenit.saturno.service.IntervaloService;
import com.zenit.saturno.domain.Intervalo;
import com.zenit.saturno.repository.IntervaloRepository;
import com.zenit.saturno.service.dto.IntervaloDTO;
import com.zenit.saturno.service.mapper.IntervaloMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Intervalo.
 */
@Service
@Transactional
public class IntervaloServiceImpl implements IntervaloService {

    private final Logger log = LoggerFactory.getLogger(IntervaloServiceImpl.class);

    private final IntervaloRepository intervaloRepository;

    private final IntervaloMapper intervaloMapper;

    public IntervaloServiceImpl(IntervaloRepository intervaloRepository, IntervaloMapper intervaloMapper) {
        this.intervaloRepository = intervaloRepository;
        this.intervaloMapper = intervaloMapper;
    }

    /**
     * Save a intervalo.
     *
     * @param intervaloDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public IntervaloDTO save(IntervaloDTO intervaloDTO) {
        log.debug("Request to save Intervalo : {}", intervaloDTO);

        Intervalo intervalo = intervaloMapper.toEntity(intervaloDTO);
        intervalo = intervaloRepository.save(intervalo);
        return intervaloMapper.toDto(intervalo);
    }

    /**
     * Get all the intervalos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IntervaloDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Intervalos");
        return intervaloRepository.findAll(pageable)
            .map(intervaloMapper::toDto);
    }


    /**
     * Get one intervalo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<IntervaloDTO> findOne(Long id) {
        log.debug("Request to get Intervalo : {}", id);
        return intervaloRepository.findById(id)
            .map(intervaloMapper::toDto);
    }

    /**
     * Delete the intervalo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Intervalo : {}", id);
        intervaloRepository.deleteById(id);
    }
}
