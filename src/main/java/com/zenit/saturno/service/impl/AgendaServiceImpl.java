package com.zenit.saturno.service.impl;

import com.zenit.saturno.service.AgendaService;
import com.zenit.saturno.domain.Agenda;
import com.zenit.saturno.repository.AgendaRepository;
import com.zenit.saturno.service.dto.AgendaDTO;
import com.zenit.saturno.service.mapper.AgendaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Agenda.
 */
@Service
@Transactional
public class AgendaServiceImpl implements AgendaService {

    private final Logger log = LoggerFactory.getLogger(AgendaServiceImpl.class);

    private final AgendaRepository agendaRepository;

    private final AgendaMapper agendaMapper;

    public AgendaServiceImpl(AgendaRepository agendaRepository, AgendaMapper agendaMapper) {
        this.agendaRepository = agendaRepository;
        this.agendaMapper = agendaMapper;
    }

    /**
     * Save a agenda.
     *
     * @param agendaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AgendaDTO save(AgendaDTO agendaDTO) {
        log.debug("Request to save Agenda : {}", agendaDTO);

        Agenda agenda = agendaMapper.toEntity(agendaDTO);
        agenda = agendaRepository.save(agenda);
        return agendaMapper.toDto(agenda);
    }

    /**
     * Get all the agenda.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AgendaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Agenda");
        return agendaRepository.findAll(pageable)
            .map(agendaMapper::toDto);
    }

    /**
     * Get all the Agenda with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<AgendaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return agendaRepository.findAllWithEagerRelationships(pageable).map(agendaMapper::toDto);
    }
    

    /**
     * Get one agenda by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AgendaDTO> findOne(Long id) {
        log.debug("Request to get Agenda : {}", id);
        return agendaRepository.findOneWithEagerRelationships(id)
            .map(agendaMapper::toDto);
    }

    /**
     * Delete the agenda by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Agenda : {}", id);
        agendaRepository.deleteById(id);
    }
}
