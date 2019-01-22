import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SaturnoTurnoModule } from './turno/turno.module';
import { SaturnoClienteModule } from './cliente/cliente.module';
import { SaturnoVehiculoModule } from './vehiculo/vehiculo.module';
import { SaturnoModeloModule } from './modelo/modelo.module';
import { SaturnoTipoDeServicioModule } from './tipo-de-servicio/tipo-de-servicio.module';
import { SaturnoServicioModule } from './servicio/servicio.module';
import { SaturnoPrecioServicioModule } from './precio-servicio/precio-servicio.module';
import { SaturnoTareaModule } from './tarea/tarea.module';
import { SaturnoAgendaModule } from './agenda/agenda.module';
import { SaturnoIntervaloModule } from './intervalo/intervalo.module';
import { SaturnoDiaNoLaborableModule } from './dia-no-laborable/dia-no-laborable.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        SaturnoTurnoModule,
        SaturnoClienteModule,
        SaturnoVehiculoModule,
        SaturnoModeloModule,
        SaturnoTipoDeServicioModule,
        SaturnoServicioModule,
        SaturnoPrecioServicioModule,
        SaturnoTareaModule,
        SaturnoAgendaModule,
        SaturnoIntervaloModule,
        SaturnoDiaNoLaborableModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SaturnoEntityModule {}
