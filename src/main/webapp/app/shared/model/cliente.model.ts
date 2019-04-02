import { ITurno } from 'app/shared/model//turno.model';

export interface ICliente {
    id?: number;
    nombre?: string;
    apellido?: string;
    email?: string;
    dni?: number;
    telefono?: string;
    celular?: string;
    turnos?: ITurno[];
}

export class Cliente implements ICliente {
    constructor(
        public id?: number,
        public nombre?: string,
        public apellido?: string,
        public email?: string,
        public dni?: number,
        public telefono?: string,
        public celular?: string,
        public turnos?: ITurno[]
    ) {}
}
