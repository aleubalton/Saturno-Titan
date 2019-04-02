package com.zenit.saturno.service.impl;

import com.zenit.saturno.service.HorarioEspecialService;
import com.zenit.saturno.domain.HorarioEspecial;
import com.zenit.saturno.repository.HorarioEspecialRepository;
import com.zenit.saturno.service.dto.HorarioEspecialDTO;
import com.zenit.saturno.service.mapper.HorarioEspecialMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing HorarioEspecial.
 */
@Service
@Transactional
public class HorarioEspecialServiceImpl implements HorarioEspecialService {

    private final Logger log = LoggerFactory.getLogger(HorarioEspecialServiceImpl.class);

    private final HorarioEspecialRepository horarioEspecialRepository;

    private final HorarioEspecialMapper horarioEspecialMapper;

    public HorarioEspecialServiceImpl(HorarioEspecialRepository horarioEspecialRepository, HorarioEspecialMapper horarioEspecialMapper) {
        this.horarioEspecialRepository = horarioEspecialRepository;
        this.horarioEspecialMapper = horarioEspecialMapper;
    }

    /**
     * Save a horarioEspecial.
     *
     * @param horarioEspecialDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public HorarioEspecialDTO save(HorarioEspecialDTO horarioEspecialDTO) {
        log.debug("Request to save HorarioEspecial : {}", horarioEspecialDTO);

        HorarioEspecial horarioEspecial = horarioEspecialMapper.toEntity(horarioEspecialDTO);
        horarioEspecial = horarioEspecialRepository.save(horarioEspecial);
        return horarioEspecialMapper.toDto(horarioEspecial);
    }

    /**
     * Get all the horarioEspecials.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<HorarioEspecialDTO> findAll(Pageable pageable) {
        log.debug("Request to get all HorarioEspecials");
        return horarioEspecialRepository.findAll(pageable)
            .map(horarioEspecialMapper::toDto);
    }


    /**
     * Get one horarioEspecial by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<HorarioEspecialDTO> findOne(Long id) {
        log.debug("Request to get HorarioEspecial : {}", id);
        return horarioEspecialRepository.findById(id)
            .map(horarioEspecialMapper::toDto);
    }

    /**
     * Delete the horarioEspecial by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete HorarioEspecial : {}", id);
        horarioEspecialRepository.deleteById(id);
    }
}
