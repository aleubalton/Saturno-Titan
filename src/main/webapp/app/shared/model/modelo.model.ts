import { IPrecioServicio } from 'app/shared/model//precio-servicio.model';
import { IVehiculo } from 'app/shared/model//vehiculo.model';

export const enum Marca {
    TOYOTA = 'TOYOTA',
    LEXUS = 'LEXUS',
    HINO = 'HINO'
}

export interface IModelo {
    id?: number;
    codigo?: string;
    nombre?: string;
    anioInicioProduccion?: number;
    anioFinProduccion?: number;
    marca?: Marca;
    precios?: IPrecioServicio[];
    planNombre?: string;
    planId?: number;
    vehiculos?: IVehiculo[];
}

export class Modelo implements IModelo {
    constructor(
        public id?: number,
        public codigo?: string,
        public nombre?: string,
        public anioInicioProduccion?: number,
        public anioFinProduccion?: number,
        public marca?: Marca,
        public precios?: IPrecioServicio[],
        public planNombre?: string,
        public planId?: number,
        public vehiculos?: IVehiculo[]
    ) {}
}
