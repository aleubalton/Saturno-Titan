package com.zenit.saturno.service.mapper;

import com.zenit.saturno.domain.*;
import com.zenit.saturno.service.dto.ServicioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Servicio and its DTO ServicioDTO.
 */
@Mapper(componentModel = "spring", uses = {TipoServicioMapper.class, TareaMapper.class})
public interface ServicioMapper extends EntityMapper<ServicioDTO, Servicio> {

    @Mapping(source = "tipo.id", target = "tipoId")
    @Mapping(source = "tipo.codigo", target = "tipoCodigo")
    ServicioDTO toDto(Servicio servicio);

    @Mapping(target = "precios", ignore = true)
    @Mapping(source = "tipoId", target = "tipo")
    @Mapping(target = "turnos", ignore = true)
    Servicio toEntity(ServicioDTO servicioDTO);

    default Servicio fromId(Long id) {
        if (id == null) {
            return null;
        }
        Servicio servicio = new Servicio();
        servicio.setId(id);
        return servicio;
    }
}
