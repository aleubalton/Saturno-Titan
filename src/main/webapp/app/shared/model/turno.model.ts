import { Moment } from 'moment';
import { IServicio } from 'app/shared/model//servicio.model';

export const enum Estado {
    RESERVADO = 'RESERVADO',
    EXPIRADO = 'EXPIRADO',
    VALIDADO = 'VALIDADO',
    CONFIRMADO = 'CONFIRMADO',
    CANCELADO = 'CANCELADO',
    FINALIZADO = 'FINALIZADO'
}

export interface ITurno {
    id?: number;
    codigoReserva?: string;
    fechaHora?: Moment;
    duracion?: number;
    costo?: number;
    estado?: Estado;
    comentario?: string;
    indicaciones?: string;
    agendaNombre?: string;
    agendaId?: number;
    vehiculoPatente?: string;
    vehiculoId?: number;
    servicios?: IServicio[];
    clienteApellido?: string;
    clienteId?: number;
}

export class Turno implements ITurno {
    constructor(
        public id?: number,
        public codigoReserva?: string,
        public fechaHora?: Moment,
        public duracion?: number,
        public costo?: number,
        public estado?: Estado,
        public comentario?: string,
        public indicaciones?: string,
        public agendaNombre?: string,
        public agendaId?: number,
        public vehiculoPatente?: string,
        public vehiculoId?: number,
        public servicios?: IServicio[],
        public clienteApellido?: string,
        public clienteId?: number
    ) {}
}
