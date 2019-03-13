import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SaturnoTurnoModule } from './turno/turno.module';
import { SaturnoClienteModule } from './cliente/cliente.module';
import { SaturnoVehiculoModule } from './vehiculo/vehiculo.module';
import { SaturnoModeloModule } from './modelo/modelo.module';
import { SaturnoPlanMantenimientoModule } from './plan-mantenimiento/plan-mantenimiento.module';
import { SaturnoTipoServicioModule } from './tipo-servicio/tipo-servicio.module';
import { SaturnoServicioModule } from './servicio/servicio.module';
import { SaturnoPrecioServicioModule } from './precio-servicio/precio-servicio.module';
import { SaturnoTareaModule } from './tarea/tarea.module';
import { SaturnoAgendaModule } from './agenda/agenda.module';
import { SaturnoHorarioModule } from './horario/horario.module';
import { SaturnoHorarioEspecialModule } from './horario-especial/horario-especial.module';
import { SaturnoDiaNoLaborableModule } from './dia-no-laborable/dia-no-laborable.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        SaturnoTurnoModule,
        SaturnoClienteModule,
        SaturnoVehiculoModule,
        SaturnoModeloModule,
        SaturnoPlanMantenimientoModule,
        SaturnoTipoServicioModule,
        SaturnoServicioModule,
        SaturnoPrecioServicioModule,
        SaturnoTareaModule,
        SaturnoAgendaModule,
        SaturnoHorarioModule,
        SaturnoHorarioEspecialModule,
        SaturnoDiaNoLaborableModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SaturnoEntityModule {}
