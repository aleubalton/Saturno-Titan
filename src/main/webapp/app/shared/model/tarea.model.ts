import { IServicio } from 'app/shared/model//servicio.model';

export const enum TipoTarea {
    CAMBIO = 'CAMBIO',
    INSPECCION = 'INSPECCION'
}

export interface ITarea {
    id?: number;
    codigo?: string;
    descripcion?: string;
    tipo?: TipoTarea;
    servicios?: IServicio[];
}

export class Tarea implements ITarea {
    constructor(
        public id?: number,
        public codigo?: string,
        public descripcion?: string,
        public tipo?: TipoTarea,
        public servicios?: IServicio[]
    ) {}
}
