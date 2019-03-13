import { IServicio } from 'app/shared/model//servicio.model';
import { IModelo } from 'app/shared/model//modelo.model';

export interface IPlanMantenimiento {
    id?: number;
    codigo?: string;
    nombre?: string;
    servicios?: IServicio[];
    modelos?: IModelo[];
}

export class PlanMantenimiento implements IPlanMantenimiento {
    constructor(
        public id?: number,
        public codigo?: string,
        public nombre?: string,
        public servicios?: IServicio[],
        public modelos?: IModelo[]
    ) {}
}
