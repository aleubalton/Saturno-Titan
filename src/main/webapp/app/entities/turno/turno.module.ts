import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaturnoSharedModule } from 'app/shared';
import {
    TurnoComponent,
    TurnoDetailComponent,
    TurnoUpdateComponent,
    TurnoDeletePopupComponent,
    TurnoDeleteDialogComponent,
    turnoRoute,
    turnoPopupRoute
} from './';

const ENTITY_STATES = [...turnoRoute, ...turnoPopupRoute];

@NgModule({
    imports: [SaturnoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [TurnoComponent, TurnoDetailComponent, TurnoUpdateComponent, TurnoDeleteDialogComponent, TurnoDeletePopupComponent],
    entryComponents: [TurnoComponent, TurnoUpdateComponent, TurnoDeleteDialogComponent, TurnoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SaturnoTurnoModule {}
