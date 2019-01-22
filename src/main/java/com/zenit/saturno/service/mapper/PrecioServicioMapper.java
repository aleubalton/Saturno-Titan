package com.zenit.saturno.service.mapper;

import com.zenit.saturno.domain.*;
import com.zenit.saturno.service.dto.PrecioServicioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PrecioServicio and its DTO PrecioServicioDTO.
 */
@Mapper(componentModel = "spring", uses = {ModeloMapper.class, ServicioMapper.class})
public interface PrecioServicioMapper extends EntityMapper<PrecioServicioDTO, PrecioServicio> {

    @Mapping(source = "modelo.id", target = "modeloId")
    @Mapping(source = "modelo.nombre", target = "modeloNombre")
    @Mapping(source = "servicio.id", target = "servicioId")
    @Mapping(source = "servicio.nombre", target = "servicioNombre")
    PrecioServicioDTO toDto(PrecioServicio precioServicio);

    @Mapping(source = "modeloId", target = "modelo")
    @Mapping(source = "servicioId", target = "servicio")
    PrecioServicio toEntity(PrecioServicioDTO precioServicioDTO);

    default PrecioServicio fromId(Long id) {
        if (id == null) {
            return null;
        }
        PrecioServicio precioServicio = new PrecioServicio();
        precioServicio.setId(id);
        return precioServicio;
    }
}
