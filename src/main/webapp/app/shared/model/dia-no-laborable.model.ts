import { Moment } from 'moment';
import { IAgenda } from 'app/shared/model//agenda.model';

export interface IDiaNoLaborable {
    id?: number;
    descripcion?: string;
    fecha?: Moment;
    agenda?: IAgenda[];
}

export class DiaNoLaborable implements IDiaNoLaborable {
    constructor(public id?: number, public descripcion?: string, public fecha?: Moment, public agenda?: IAgenda[]) {}
}
