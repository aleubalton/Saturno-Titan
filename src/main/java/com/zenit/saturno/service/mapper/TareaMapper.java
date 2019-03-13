package com.zenit.saturno.service.mapper;

import com.zenit.saturno.domain.*;
import com.zenit.saturno.service.dto.TareaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Tarea and its DTO TareaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TareaMapper extends EntityMapper<TareaDTO, Tarea> {


    @Mapping(target = "servicios", ignore = true)
    Tarea toEntity(TareaDTO tareaDTO);

    default Tarea fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tarea tarea = new Tarea();
        tarea.setId(id);
        return tarea;
    }
}
