import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaturnoSharedModule } from 'app/shared';
import {
    IntervaloComponent,
    IntervaloDetailComponent,
    IntervaloUpdateComponent,
    IntervaloDeletePopupComponent,
    IntervaloDeleteDialogComponent,
    intervaloRoute,
    intervaloPopupRoute
} from './';

const ENTITY_STATES = [...intervaloRoute, ...intervaloPopupRoute];

@NgModule({
    imports: [SaturnoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        IntervaloComponent,
        IntervaloDetailComponent,
        IntervaloUpdateComponent,
        IntervaloDeleteDialogComponent,
        IntervaloDeletePopupComponent
    ],
    entryComponents: [IntervaloComponent, IntervaloUpdateComponent, IntervaloDeleteDialogComponent, IntervaloDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SaturnoIntervaloModule {}
