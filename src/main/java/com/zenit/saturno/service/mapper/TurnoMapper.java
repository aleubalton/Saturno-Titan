package com.zenit.saturno.service.mapper;

import com.zenit.saturno.domain.*;
import com.zenit.saturno.service.dto.TurnoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Turno and its DTO TurnoDTO.
 */
@Mapper(componentModel = "spring", uses = {AgendaMapper.class, VehiculoMapper.class, ServicioMapper.class, ClienteMapper.class})
public interface TurnoMapper extends EntityMapper<TurnoDTO, Turno> {

    @Mapping(source = "agenda.id", target = "agendaId")
    @Mapping(source = "agenda.nombre", target = "agendaNombre")
    @Mapping(source = "vehiculo.id", target = "vehiculoId")
    @Mapping(source = "vehiculo.patente", target = "vehiculoPatente")
    @Mapping(source = "vehiculo.modelo.nombre", target = "vehiculoModelo")
    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "cliente.apellido", target = "clienteApellido")
    @Mapping(source = "cliente.nombre", target = "clienteNombre")
    @Mapping(source = "cliente.email", target = "clienteEmail")
    TurnoDTO toDto(Turno turno);

    @Mapping(source = "agendaId", target = "agenda")
    @Mapping(source = "vehiculoId", target = "vehiculo")
    @Mapping(source = "clienteId", target = "cliente")
    Turno toEntity(TurnoDTO turnoDTO);

    default Turno fromId(Long id) {
        if (id == null) {
            return null;
        }
        Turno turno = new Turno();
        turno.setId(id);
        return turno;
    }
}
