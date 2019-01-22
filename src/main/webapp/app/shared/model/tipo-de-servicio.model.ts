import { IServicio } from 'app/shared/model//servicio.model';

export const enum TipoRecurso {
    BAHIA = 'BAHIA',
    LAVADERO = 'LAVADERO',
    ASESOR = 'ASESOR',
    TECNICO = 'TECNICO'
}

export interface ITipoDeServicio {
    id?: number;
    nombre?: string;
    codigo?: string;
    interno?: boolean;
    tipoDeRecurso?: TipoRecurso;
    servicios?: IServicio[];
}

export class TipoDeServicio implements ITipoDeServicio {
    constructor(
        public id?: number,
        public nombre?: string,
        public codigo?: string,
        public interno?: boolean,
        public tipoDeRecurso?: TipoRecurso,
        public servicios?: IServicio[]
    ) {
        this.interno = this.interno || false;
    }
}
