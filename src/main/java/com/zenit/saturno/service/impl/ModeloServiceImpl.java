package com.zenit.saturno.service.impl;

import com.zenit.saturno.service.ModeloService;
import com.zenit.saturno.domain.Modelo;
import com.zenit.saturno.repository.ModeloRepository;
import com.zenit.saturno.service.dto.ModeloDTO;
import com.zenit.saturno.service.mapper.ModeloMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Modelo.
 */
@Service
@Transactional
public class ModeloServiceImpl implements ModeloService {

    private final Logger log = LoggerFactory.getLogger(ModeloServiceImpl.class);

    private final ModeloRepository modeloRepository;

    private final ModeloMapper modeloMapper;

    public ModeloServiceImpl(ModeloRepository modeloRepository, ModeloMapper modeloMapper) {
        this.modeloRepository = modeloRepository;
        this.modeloMapper = modeloMapper;
    }

    /**
     * Save a modelo.
     *
     * @param modeloDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ModeloDTO save(ModeloDTO modeloDTO) {
        log.debug("Request to save Modelo : {}", modeloDTO);

        Modelo modelo = modeloMapper.toEntity(modeloDTO);
        modelo = modeloRepository.save(modelo);
        return modeloMapper.toDto(modelo);
    }

    /**
     * Get all the modelos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ModeloDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Modelos");
        return modeloRepository.findAll(pageable)
            .map(modeloMapper::toDto);
    }


    /**
     * Get one modelo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ModeloDTO> findOne(Long id) {
        log.debug("Request to get Modelo : {}", id);
        return modeloRepository.findById(id)
            .map(modeloMapper::toDto);
    }

    /**
     * Delete the modelo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Modelo : {}", id);
        modeloRepository.deleteById(id);
    }
}