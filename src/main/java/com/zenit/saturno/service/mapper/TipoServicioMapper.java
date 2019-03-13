package com.zenit.saturno.service.mapper;

import com.zenit.saturno.domain.*;
import com.zenit.saturno.service.dto.TipoServicioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TipoServicio and its DTO TipoServicioDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoServicioMapper extends EntityMapper<TipoServicioDTO, TipoServicio> {


    @Mapping(target = "servicios", ignore = true)
    TipoServicio toEntity(TipoServicioDTO tipoServicioDTO);

    default TipoServicio fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoServicio tipoServicio = new TipoServicio();
        tipoServicio.setId(id);
        return tipoServicio;
    }
}
