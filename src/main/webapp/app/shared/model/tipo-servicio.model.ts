import { IServicio } from 'app/shared/model//servicio.model';

export const enum TipoRecurso {
    BAHIA = 'BAHIA',
    LAVADERO = 'LAVADERO',
    ASESOR = 'ASESOR',
    TECNICO = 'TECNICO'
}

export interface ITipoServicio {
    id?: number;
    nombre?: string;
    codigo?: string;
    interno?: boolean;
    tipoRecurso?: TipoRecurso;
    servicios?: IServicio[];
}

export class TipoServicio implements ITipoServicio {
    constructor(
        public id?: number,
        public nombre?: string,
        public codigo?: string,
        public interno?: boolean,
        public tipoRecurso?: TipoRecurso,
        public servicios?: IServicio[]
    ) {
        this.interno = this.interno || false;
    }
}
