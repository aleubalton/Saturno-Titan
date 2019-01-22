package com.zenit.saturno.service.mapper;

import com.zenit.saturno.domain.*;
import com.zenit.saturno.service.dto.IntervaloDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Intervalo and its DTO IntervaloDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface IntervaloMapper extends EntityMapper<IntervaloDTO, Intervalo> {


    @Mapping(target = "agenda", ignore = true)
    Intervalo toEntity(IntervaloDTO intervaloDTO);

    default Intervalo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Intervalo intervalo = new Intervalo();
        intervalo.setId(id);
        return intervalo;
    }
}
