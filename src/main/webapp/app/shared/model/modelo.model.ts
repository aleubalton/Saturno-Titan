import { IPrecioServicio } from 'app/shared/model//precio-servicio.model';
import { IVehiculo } from 'app/shared/model//vehiculo.model';

export const enum Marca {
    TOYOTA = 'TOYOTA',
    LEXUS = 'LEXUS',
    HINO = 'HINO'
}

export const enum Categoria {
    AUTOMOVIL = 'AUTOMOVIL',
    CARGA = 'CARGA',
    REMOLQUE = 'REMOLQUE',
    TRANSPORTE = 'TRANSPORTE'
}

export interface IModelo {
    id?: number;
    codigo?: string;
    nombre?: string;
    anioInicioProduccion?: number;
    anioFinProduccion?: number;
    marca?: Marca;
    categoria?: Categoria;
    precios?: IPrecioServicio[];
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
        public categoria?: Categoria,
        public precios?: IPrecioServicio[],
        public vehiculos?: IVehiculo[]
    ) {}
}
