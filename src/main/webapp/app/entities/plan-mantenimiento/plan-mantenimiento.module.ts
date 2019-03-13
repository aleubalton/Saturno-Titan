import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaturnoSharedModule } from 'app/shared';
import {
    PlanMantenimientoComponent,
    PlanMantenimientoDetailComponent,
    PlanMantenimientoUpdateComponent,
    PlanMantenimientoDeletePopupComponent,
    PlanMantenimientoDeleteDialogComponent,
    planMantenimientoRoute,
    planMantenimientoPopupRoute
} from './';

const ENTITY_STATES = [...planMantenimientoRoute, ...planMantenimientoPopupRoute];

@NgModule({
    imports: [SaturnoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PlanMantenimientoComponent,
        PlanMantenimientoDetailComponent,
        PlanMantenimientoUpdateComponent,
        PlanMantenimientoDeleteDialogComponent,
        PlanMantenimientoDeletePopupComponent
    ],
    entryComponents: [
        PlanMantenimientoComponent,
        PlanMantenimientoUpdateComponent,
        PlanMantenimientoDeleteDialogComponent,
        PlanMantenimientoDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SaturnoPlanMantenimientoModule {}
