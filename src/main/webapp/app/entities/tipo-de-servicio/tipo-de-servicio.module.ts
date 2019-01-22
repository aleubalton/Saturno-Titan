import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaturnoSharedModule } from 'app/shared';
import {
    TipoDeServicioComponent,
    TipoDeServicioDetailComponent,
    TipoDeServicioUpdateComponent,
    TipoDeServicioDeletePopupComponent,
    TipoDeServicioDeleteDialogComponent,
    tipoDeServicioRoute,
    tipoDeServicioPopupRoute
} from './';

const ENTITY_STATES = [...tipoDeServicioRoute, ...tipoDeServicioPopupRoute];

@NgModule({
    imports: [SaturnoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TipoDeServicioComponent,
        TipoDeServicioDetailComponent,
        TipoDeServicioUpdateComponent,
        TipoDeServicioDeleteDialogComponent,
        TipoDeServicioDeletePopupComponent
    ],
    entryComponents: [
        TipoDeServicioComponent,
        TipoDeServicioUpdateComponent,
        TipoDeServicioDeleteDialogComponent,
        TipoDeServicioDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SaturnoTipoDeServicioModule {}
