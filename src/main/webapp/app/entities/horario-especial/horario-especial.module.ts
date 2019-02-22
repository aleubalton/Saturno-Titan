import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaturnoSharedModule } from 'app/shared';
import {
    HorarioEspecialComponent,
    HorarioEspecialDetailComponent,
    HorarioEspecialUpdateComponent,
    HorarioEspecialDeletePopupComponent,
    HorarioEspecialDeleteDialogComponent,
    horarioEspecialRoute,
    horarioEspecialPopupRoute
} from './';

const ENTITY_STATES = [...horarioEspecialRoute, ...horarioEspecialPopupRoute];

@NgModule({
    imports: [SaturnoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        HorarioEspecialComponent,
        HorarioEspecialDetailComponent,
        HorarioEspecialUpdateComponent,
        HorarioEspecialDeleteDialogComponent,
        HorarioEspecialDeletePopupComponent
    ],
    entryComponents: [
        HorarioEspecialComponent,
        HorarioEspecialUpdateComponent,
        HorarioEspecialDeleteDialogComponent,
        HorarioEspecialDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SaturnoHorarioEspecialModule {}
