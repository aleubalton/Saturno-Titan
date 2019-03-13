package com.zenit.saturno.service.mapper;

import com.zenit.saturno.domain.*;
import com.zenit.saturno.service.dto.ModeloDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Modelo and its DTO ModeloDTO.
 */
@Mapper(componentModel = "spring", uses = {PlanMantenimientoMapper.class})
public interface ModeloMapper extends EntityMapper<ModeloDTO, Modelo> {

    @Mapping(source = "plan.id", target = "planId")
    @Mapping(source = "plan.nombre", target = "planNombre")
    ModeloDTO toDto(Modelo modelo);

    @Mapping(target = "precios", ignore = true)
    @Mapping(source = "planId", target = "plan")
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
