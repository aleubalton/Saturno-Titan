import { ITurno } from 'app/shared/model//turno.model';

export interface IVehiculo {
    id?: number;
    patente?: string;
    anio?: number;
    kilometraje?: number;
    vin?: string;
    motor?: string;
    modeloNombre?: string;
    modeloId?: number;
    turnos?: ITurno[];
}

export class Vehiculo implements IVehiculo {
    constructor(
        public id?: number,
        public patente?: string,
        public anio?: number,
        public kilometraje?: number,
        public vin?: string,
        public motor?: string,
        public modeloNombre?: string,
        public modeloId?: number,
        public turnos?: ITurno[]
    ) {}
}
