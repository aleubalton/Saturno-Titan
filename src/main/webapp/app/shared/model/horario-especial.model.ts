import { Moment } from 'moment';
import { IAgenda } from 'app/shared/model//agenda.model';

export interface IHorarioEspecial {
    id?: number;
    descripcion?: string;
    hora?: number;
    duracion?: number;
    fecha?: Moment;
    agenda?: IAgenda[];
}

export class HorarioEspecial implements IHorarioEspecial {
    constructor(
        public id?: number,
        public descripcion?: string,
        public hora?: number,
        public duracion?: number,
        public fecha?: Moment,
        public agenda?: IAgenda[]
    ) {}
}
