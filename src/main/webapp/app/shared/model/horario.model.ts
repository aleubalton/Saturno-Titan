import { IAgenda } from 'app/shared/model//agenda.model';

export const enum Dia {
    LUNES = 'LUNES',
    MARTES = 'MARTES',
    MIERCOLES = 'MIERCOLES',
    JUEVES = 'JUEVES',
    VIERNES = 'VIERNES',
    SABADO = 'SABADO',
    DOMINGO = 'DOMINGO'
}

export interface IHorario {
    id?: number;
    descripcion?: string;
    horaDesde?: number;
    horaHasta?: number;
    dia?: Dia;
    agenda?: IAgenda[];
}

export class Horario implements IHorario {
    constructor(
        public id?: number,
        public descripcion?: string,
        public horaDesde?: number,
        public horaHasta?: number,
        public dia?: Dia,
        public agenda?: IAgenda[]
    ) {}
}
