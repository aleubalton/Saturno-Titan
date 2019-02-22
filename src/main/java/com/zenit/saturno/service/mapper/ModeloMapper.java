package com.zenit.saturno.service.mapper;

import com.zenit.saturno.domain.*;
import com.zenit.saturno.service.dto.ModeloDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Modelo and its DTO ModeloDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ModeloMapper extends EntityMapper<ModeloDTO, Modelo> {


    @Mapping(target = "precios", ignore = true)
    @Mapping(target = "vehiculos", ignore = true)
    Modelo toEntity(ModeloDTO modeloDTO);

    default Modelo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Modelo modelo = new Modelo();
        modelo.setId(id);
        return modelo;
    }
}
