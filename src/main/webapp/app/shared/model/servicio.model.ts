import { ITarea } from 'app/shared/model//tarea.model';
import { ITurno } from 'app/shared/model//turno.model';

export interface IServicio {
    id?: number;
    nombre?: string;
    codigo?: string;
    kilometraje?: number;
    duracion?: number;
    precio?: number;
    tipoCodigo?: string;
    tipoNombre?: string;
    tipoId?: number;
    tareas?: ITarea[];
    planNombre?: string;
    planId?: number;
    turnos?: ITurno[];
}

export class Servicio implements IServicio {
    constructor(
        public id?: number,
        public nombre?: string,
        public codigo?: string,
        public kilometraje?: number,
        public duracion?: number,
        public precio?: number,
        public tipoCodigo?: string,
        public tipoNombre?: string,
        public tipoId?: number,
        public tareas?: ITarea[],
        public planNombre?: string,
        public planId?: number,
        public turnos?: ITurno[]
    ) {}
}
