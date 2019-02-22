import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaturnoSharedModule } from 'app/shared';
import {
    DiaNoLaborableComponent,
    DiaNoLaborableDetailComponent,
    DiaNoLaborableUpdateComponent,
    DiaNoLaborableDeletePopupComponent,
    DiaNoLaborableDeleteDialogComponent,
    diaNoLaborableRoute,
    diaNoLaborablePopupRoute
} from './';

const ENTITY_STATES = [...diaNoLaborableRoute, ...diaNoLaborablePopupRoute];

@NgModule({
    imports: [SaturnoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DiaNoLaborableComponent,
        DiaNoLaborableDetailComponent,
        DiaNoLaborableUpdateComponent,
        DiaNoLaborableDeleteDialogComponent,
        DiaNoLaborableDeletePopupComponent
    ],
    entryComponents: [
        DiaNoLaborableComponent,
        DiaNoLaborableUpdateComponent,
        DiaNoLaborableDeleteDialogComponent,
        DiaNoLaborableDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SaturnoDiaNoLaborableModule {}
