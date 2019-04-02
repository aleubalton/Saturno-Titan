import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaturnoSharedModule } from 'app/shared';
import {
    AgendaComponent,
    AgendaDetailComponent,
    AgendaUpdateComponent,
    AgendaDeletePopupComponent,
    AgendaDeleteDialogComponent,
    agendaRoute,
    agendaPopupRoute
} from './';

const ENTITY_STATES = [...agendaRoute, ...agendaPopupRoute];

@NgModule({
    imports: [SaturnoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [AgendaComponent, AgendaDetailComponent, AgendaUpdateComponent, AgendaDeleteDialogComponent, AgendaDeletePopupComponent],
    entryComponents: [AgendaComponent, AgendaUpdateComponent, AgendaDeleteDialogComponent, AgendaDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SaturnoAgendaModule {}
