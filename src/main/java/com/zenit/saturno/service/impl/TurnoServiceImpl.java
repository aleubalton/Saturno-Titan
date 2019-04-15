package com.zenit.saturno.service.impl;

import com.zenit.saturno.service.TurnoService;
import com.zenit.saturno.domain.Turno;
import com.zenit.saturno.repository.TurnoRepository;
import com.zenit.saturno.service.dto.TurnoDTO;
import com.zenit.saturno.service.mapper.TurnoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.time.Instant;

/**
 * Service Implementation for managing Turno.
 */
@Service
@Transactional
public class TurnoServiceImpl implements TurnoService {

    private final Logger log = LoggerFactory.getLogger(TurnoServiceImpl.class);

    private final TurnoRepository turnoRepository;

    private final TurnoMapper turnoMapper;

    public TurnoServiceImpl(TurnoRepository turnoRepository, TurnoMapper turnoMapper) {
        this.turnoRepository = turnoRepository;
        this.turnoMapper = turnoMapper;
    }

    /**
     * Save a turno.
     *
     * @param turnoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TurnoDTO save(TurnoDTO turnoDTO) {
        log.debug("Request to save Turno : {}", turnoDTO);

        Turno turno = turnoMapper.toEntity(turnoDTO);
        turno = turnoRepository.save(turno);
        return turnoMapper.toDto(turno);
    }

    /**
     * Get all the turnos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional
    public Page<TurnoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Turnos");
        turnoRepository.expirarTurnos(Instant.now().minusSeconds(3600));
        return turnoRepository.findAll(pageable)
            .map(turnoMapper::toDto);
    }

    /**
     * Get all the Turno with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<TurnoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return turnoRepository.findAllWithEagerRelationships(pageable).map(turnoMapper::toDto);
    }


    /**
     * Get one turno by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TurnoDTO> findOne(Long id) {
        log.debug("Request to get Turno : {}", id);
        return turnoRepository.findOneWithEagerRelationships(id)
            .map(turnoMapper::toDto);
    }

    /**
     * Get one turno by codigoReserva.
     *
     * @param codigoReserva the codigoReserva of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TurnoDTO> findByCodigoReserva(String codigoReserva) {
        log.debug("Request to get Turno : {}", codigoReserva);
        turnoRepository.expirarTurnos(Instant.now().minusSeconds(3600));
        return turnoRepository.findByCodigoReserva(codigoReserva)
            .map(turnoMapper::toDto);
    }

    /**
     * Get the "year-month-day" turno.
     *
     * @param year the year of the entity
     * @param month the month of the entity
     * @param day the day of the entity
     * @param agendaId the id of the agenda
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TurnoDTO> findAllByFecha(Pageable pageable, Integer year, Integer month, Integer day, Integer agendaId) {
        turnoRepository.expirarTurnos(Instant.now().minusSeconds(3600));
        if (agendaId != 0) {
            log.debug("Request to get Turnos by fecha : {} - {} - {} / Agenda ID: {}", year, month, day, agendaId);
            return turnoRepository.findAllByFechaAndAgenda(pageable, year, month, day, Long.valueOf(agendaId))
                .map(turnoMapper::toDto);
        } else {
            log.debug("Request to get Turnos by fecha : {} - {} - {}", year, month, day);
            return turnoRepository.findAllByFecha(pageable, year, month, day)
                .map(turnoMapper::toDto);
        }
    }

    /**
     * Delete the turno by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Turno : {}", id);
        turnoRepository.deleteById(id);
    }
}
