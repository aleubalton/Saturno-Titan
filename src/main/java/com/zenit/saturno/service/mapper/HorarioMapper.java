package com.zenit.saturno.service.mapper;

import com.zenit.saturno.domain.*;
import com.zenit.saturno.service.dto.HorarioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Horario and its DTO HorarioDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HorarioMapper extends EntityMapper<HorarioDTO, Horario> {


    @Mapping(target = "agenda", ignore = true)
    Horario toEntity(HorarioDTO horarioDTO);

    default Horario fromId(Long id) {
        if (id == null) {
            return null;
        }
        Horario horario = new Horario();
        horario.setId(id);
        return horario;
    }
}
