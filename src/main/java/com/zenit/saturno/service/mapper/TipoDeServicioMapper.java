package com.zenit.saturno.service.mapper;

import com.zenit.saturno.domain.*;
import com.zenit.saturno.service.dto.TipoDeServicioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TipoDeServicio and its DTO TipoDeServicioDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoDeServicioMapper extends EntityMapper<TipoDeServicioDTO, TipoDeServicio> {


    @Mapping(target = "servicios", ignore = true)
    TipoDeServicio toEntity(TipoDeServicioDTO tipoDeServicioDTO);

    default TipoDeServicio fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoDeServicio tipoDeServicio = new TipoDeServicio();
        tipoDeServicio.setId(id);
        return tipoDeServicio;
    }
}
