package com.zenit.saturno.service.mapper;

import com.zenit.saturno.domain.*;
import com.zenit.saturno.service.dto.PlanMantenimientoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PlanMantenimiento and its DTO PlanMantenimientoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PlanMantenimientoMapper extends EntityMapper<PlanMantenimientoDTO, PlanMantenimiento> {


    @Mapping(target = "servicios", ignore = true)
    @Mapping(target = "modelos", ignore = true)
    PlanMantenimiento toEntity(PlanMantenimientoDTO planMantenimientoDTO);

    default PlanMantenimiento fromId(Long id) {
        if (id == null) {
            return null;
        }
        PlanMantenimiento planMantenimiento = new PlanMantenimiento();
        planMantenimiento.setId(id);
        return planMantenimiento;
    }
}
