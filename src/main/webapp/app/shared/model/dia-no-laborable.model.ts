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

export interface IDiaNoLaborable {
    id?: number;
    fecha?: Moment;
    dia?: Dia;
    repite?: boolean;
    agenda?: IAgenda[];
}

export class DiaNoLaborable implements IDiaNoLaborable {
    constructor(public id?: number, public fecha?: Moment, public dia?: Dia, public repite?: boolean, public agenda?: IAgenda[]) {
        this.repite = this.repite || false;
    }
}
