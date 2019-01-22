package com.zenit.saturno.service.mapper;

import com.zenit.saturno.domain.*;
import com.zenit.saturno.service.dto.AgendaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Agenda and its DTO AgendaDTO.
 */
@Mapper(componentModel = "spring", uses = {IntervaloMapper.class, DiaNoLaborableMapper.class})
public interface AgendaMapper extends EntityMapper<AgendaDTO, Agenda> {


    @Mapping(target = "turnos", ignore = true)
    Agenda toEntity(AgendaDTO agendaDTO);

    default Agenda fromId(Long id) {
        if (id == null) {
            return null;
        }
        Agenda agenda = new Agenda();
        agenda.setId(id);
        return agenda;
    }
}
