package com.zenit.saturno.service.impl;

import com.zenit.saturno.service.PlanMantenimientoService;
import com.zenit.saturno.domain.PlanMantenimiento;
import com.zenit.saturno.repository.PlanMantenimientoRepository;
import com.zenit.saturno.service.dto.PlanMantenimientoDTO;
import com.zenit.saturno.service.mapper.PlanMantenimientoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing PlanMantenimiento.
 */
@Service
@Transactional
public class PlanMantenimientoServiceImpl implements PlanMantenimientoService {

    private final Logger log = LoggerFactory.getLogger(PlanMantenimientoServiceImpl.class);

    private final PlanMantenimientoRepository planMantenimientoRepository;

    private final PlanMantenimientoMapper planMantenimientoMapper;

    public PlanMantenimientoServiceImpl(PlanMantenimientoRepository planMantenimientoRepository, PlanMantenimientoMapper planMantenimientoMapper) {
        this.planMantenimientoRepository = planMantenimientoRepository;
        this.planMantenimientoMapper = planMantenimientoMapper;
    }

    /**
     * Save a planMantenimiento.
     *
     * @param planMantenimientoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PlanMantenimientoDTO save(PlanMantenimientoDTO planMantenimientoDTO) {
        log.debug("Request to save PlanMantenimiento : {}", planMantenimientoDTO);

        PlanMantenimiento planMantenimiento = planMantenimientoMapper.toEntity(planMantenimientoDTO);
        planMantenimiento = planMantenimientoRepository.save(planMantenimiento);
        return planMantenimientoMapper.toDto(planMantenimiento);
    }

    /**
     * Get all the planMantenimientos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PlanMantenimientoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PlanMantenimientos");
        return planMantenimientoRepository.findAll(pageable)
            .map(planMantenimientoMapper::toDto);
    }


    /**
     * Get one planMantenimiento by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PlanMantenimientoDTO> findOne(Long id) {
        log.debug("Request to get PlanMantenimiento : {}", id);
        return planMantenimientoRepository.findById(id)
            .map(planMantenimientoMapper::toDto);
    }

    /**
     * Delete the planMantenimiento by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PlanMantenimiento : {}", id);
        planMantenimientoRepository.deleteById(id);
    }
}
