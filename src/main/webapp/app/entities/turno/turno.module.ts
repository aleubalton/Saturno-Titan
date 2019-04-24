import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';

import { SaturnoSharedModule } from 'app/shared';
import {
    CalendarioComponent,
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
    imports: [
        BrowserAnimationsModule,
        CalendarModule.forRoot({ provide: DateAdapter, useFactory: adapterFactory }),
        SaturnoSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CalendarioComponent,
        TurnoComponent,
        TurnoDetailComponent,
        TurnoUpdateComponent,
        TurnoDeleteDialogComponent,
        TurnoDeletePopupComponent
    ],
    entryComponents: [CalendarioComponent, TurnoComponent, TurnoUpdateComponent, TurnoDeleteDialogComponent, TurnoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SaturnoTurnoModule {}
