package com.zenit.saturno.service.impl;

import com.zenit.saturno.service.DiaNoLaborableService;
import com.zenit.saturno.domain.DiaNoLaborable;
import com.zenit.saturno.repository.DiaNoLaborableRepository;
import com.zenit.saturno.service.dto.DiaNoLaborableDTO;
import com.zenit.saturno.service.mapper.DiaNoLaborableMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing DiaNoLaborable.
 */
@Service
@Transactional
public class DiaNoLaborableServiceImpl implements DiaNoLaborableService {

    private final Logger log = LoggerFactory.getLogger(DiaNoLaborableServiceImpl.class);

    private final DiaNoLaborableRepository diaNoLaborableRepository;

    private final DiaNoLaborableMapper diaNoLaborableMapper;

    public DiaNoLaborableServiceImpl(DiaNoLaborableRepository diaNoLaborableRepository, DiaNoLaborableMapper diaNoLaborableMapper) {
        this.diaNoLaborableRepository = diaNoLaborableRepository;
        this.diaNoLaborableMapper = diaNoLaborableMapper;
    }

    /**
     * Save a diaNoLaborable.
     *
     * @param diaNoLaborableDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DiaNoLaborableDTO save(DiaNoLaborableDTO diaNoLaborableDTO) {
        log.debug("Request to save DiaNoLaborable : {}", diaNoLaborableDTO);

        DiaNoLaborable diaNoLaborable = diaNoLaborableMapper.toEntity(diaNoLaborableDTO);
        diaNoLaborable = diaNoLaborableRepository.save(diaNoLaborable);
        return diaNoLaborableMapper.toDto(diaNoLaborable);
    }

    /**
     * Get all the diaNoLaborables.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DiaNoLaborableDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DiaNoLaborables");
        return diaNoLaborableRepository.findAll(pageable)
            .map(diaNoLaborableMapper::toDto);
    }


    /**
     * Get one diaNoLaborable by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DiaNoLaborableDTO> findOne(Long id) {
        log.debug("Request to get DiaNoLaborable : {}", id);
        return diaNoLaborableRepository.findById(id)
            .map(diaNoLaborableMapper::toDto);
    }

    /**
     * Delete the diaNoLaborable by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DiaNoLaborable : {}", id);
        diaNoLaborableRepository.deleteById(id);
    }
}
