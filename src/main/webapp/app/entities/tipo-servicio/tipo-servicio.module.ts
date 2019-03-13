import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaturnoSharedModule } from 'app/shared';
import {
    TipoServicioComponent,
    TipoServicioDetailComponent,
    TipoServicioUpdateComponent,
    TipoServicioDeletePopupComponent,
    TipoServicioDeleteDialogComponent,
    tipoServicioRoute,
    tipoServicioPopupRoute
} from './';

const ENTITY_STATES = [...tipoServicioRoute, ...tipoServicioPopupRoute];

@NgModule({
    imports: [SaturnoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TipoServicioComponent,
        TipoServicioDetailComponent,
        TipoServicioUpdateComponent,
        TipoServicioDeleteDialogComponent,
        TipoServicioDeletePopupComponent
    ],
    entryComponents: [
        TipoServicioComponent,
        TipoServicioUpdateComponent,
        TipoServicioDeleteDialogComponent,
        TipoServicioDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SaturnoTipoServicioModule {}
