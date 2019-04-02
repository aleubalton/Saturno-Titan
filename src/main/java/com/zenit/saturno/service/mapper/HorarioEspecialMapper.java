package com.zenit.saturno.service.mapper;

import com.zenit.saturno.domain.*;
import com.zenit.saturno.service.dto.HorarioEspecialDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity HorarioEspecial and its DTO HorarioEspecialDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HorarioEspecialMapper extends EntityMapper<HorarioEspecialDTO, HorarioEspecial> {


    @Mapping(target = "agenda", ignore = true)
    HorarioEspecial toEntity(HorarioEspecialDTO horarioEspecialDTO);

    default HorarioEspecial fromId(Long id) {
        if (id == null) {
            return null;
        }
        HorarioEspecial horarioEspecial = new HorarioEspecial();
        horarioEspecial.setId(id);
        return horarioEspecial;
    }
}
