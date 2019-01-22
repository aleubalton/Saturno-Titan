import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaturnoSharedModule } from 'app/shared';
import {
    PrecioServicioComponent,
    PrecioServicioDetailComponent,
    PrecioServicioUpdateComponent,
    PrecioServicioDeletePopupComponent,
    PrecioServicioDeleteDialogComponent,
    precioServicioRoute,
    precioServicioPopupRoute
} from './';

const ENTITY_STATES = [...precioServicioRoute, ...precioServicioPopupRoute];

@NgModule({
    imports: [SaturnoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PrecioServicioComponent,
        PrecioServicioDetailComponent,
        PrecioServicioUpdateComponent,
        PrecioServicioDeleteDialogComponent,
        PrecioServicioDeletePopupComponent
    ],
    entryComponents: [
        PrecioServicioComponent,
        PrecioServicioUpdateComponent,
        PrecioServicioDeleteDialogComponent,
        PrecioServicioDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SaturnoPrecioServicioModule {}
