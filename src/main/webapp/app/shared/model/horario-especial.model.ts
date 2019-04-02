import { Moment } from 'moment';
import { IAgenda } from 'app/shared/model//agenda.model';

export interface IHorarioEspecial {
    id?: number;
    descripcion?: string;
    horaDesde?: number;
    horaHasta?: number;
    fecha?: Moment;
    agenda?: IAgenda[];
}

export class HorarioEspecial implements IHorarioEspecial {
    constructor(
        public id?: number,
        public descripcion?: string,
        public horaDesde?: number,
        public horaHasta?: number,
        public fecha?: Moment,
        public agenda?: IAgenda[]
    ) {}
}
