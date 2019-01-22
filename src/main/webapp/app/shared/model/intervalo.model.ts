import { Moment } from 'moment';
import { IAgenda } from 'app/shared/model//agenda.model';

export const enum Dia {
    LUNES = 'LUNES',
    MARTES = 'MARTES',
    MIERCOLES = 'MIERCOLES',
    JUEVES = 'JUEVES',
    VIERNES = 'VIERNES',
    SABADO = 'SABADO',
    DOMINGO = 'DOMINGO',
    TODOS = 'TODOS'
}

export interface IIntervalo {
    id?: number;
    fechaHoraDesde?: Moment;
    duracion?: number;
    dia?: Dia;
    repite?: boolean;
    agenda?: IAgenda[];
}

export class Intervalo implements IIntervalo {
    constructor(
        public id?: number,
        public fechaHoraDesde?: Moment,
        public duracion?: number,
        public dia?: Dia,
        public repite?: boolean,
        public agenda?: IAgenda[]
    ) {
        this.repite = this.repite || false;
    }
}
