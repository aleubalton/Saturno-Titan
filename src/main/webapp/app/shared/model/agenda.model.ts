import { Moment } from 'moment';
import { IHorario } from 'app/shared/model//horario.model';
import { IHorarioEspecial } from 'app/shared/model//horario-especial.model';
import { IDiaNoLaborable } from 'app/shared/model//dia-no-laborable.model';
import { ITurno } from 'app/shared/model//turno.model';

export const enum TipoRecurso {
    BAHIA = 'BAHIA',
    LAVADERO = 'LAVADERO',
    ASESOR = 'ASESOR',
    TECNICO = 'TECNICO'
}

export interface IAgenda {
    id?: number;
    nombre?: string;
    tipoRecurso?: TipoRecurso;
    fechaDesde?: Moment;
    fechaHasta?: Moment;
    activa?: boolean;
    horarios?: IHorario[];
    horarioEspecials?: IHorarioEspecial[];
    diaNoLaborables?: IDiaNoLaborable[];
    turnos?: ITurno[];
}

export class Agenda implements IAgenda {
    constructor(
        public id?: number,
        public nombre?: string,
        public tipoRecurso?: TipoRecurso,
        public fechaDesde?: Moment,
        public fechaHasta?: Moment,
        public activa?: boolean,
        public horarios?: IHorario[],
        public horarioEspecials?: IHorarioEspecial[],
        public diaNoLaborables?: IDiaNoLaborable[],
        public turnos?: ITurno[]
    ) {
        this.activa = this.activa || false;
    }
}
