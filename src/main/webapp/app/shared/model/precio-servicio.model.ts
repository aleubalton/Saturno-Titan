export interface IPrecioServicio {
    id?: number;
    precio?: number;
    modeloNombre?: string;
    modeloId?: number;
    servicioNombre?: string;
    servicioId?: number;
}

export class PrecioServicio implements IPrecioServicio {
    constructor(
        public id?: number,
        public precio?: number,
        public modeloNombre?: string,
        public modeloId?: number,
        public servicioNombre?: string,
        public servicioId?: number
    ) {}
}
