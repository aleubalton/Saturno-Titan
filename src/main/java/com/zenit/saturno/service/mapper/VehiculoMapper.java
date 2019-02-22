package com.zenit.saturno.service.mapper;

import com.zenit.saturno.domain.*;
import com.zenit.saturno.service.dto.VehiculoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Vehiculo and its DTO VehiculoDTO.
 */
@Mapper(componentModel = "spring", uses = {ModeloMapper.class})
public interface VehiculoMapper extends EntityMapper<VehiculoDTO, Vehiculo> {

    @Mapping(source = "modelo.id", target = "modeloId")
    @Mapping(source = "modelo.nombre", target = "modeloNombre")
    VehiculoDTO toDto(Vehiculo vehiculo);

    @Mapping(source = "modeloId", target = "modelo")
    @Mapping(target = "turnos", ignore = true)
    Vehiculo toEntity(VehiculoDTO vehiculoDTO);

    default Vehiculo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(id);
        return vehiculo;
    }
}
