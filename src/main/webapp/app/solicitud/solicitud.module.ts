import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaturnoSharedModule } from '../shared';

import { SOLICITUD_ROUTE, SolicitudComponent, SolicitudService } from './';

@NgModule({
    imports: [SaturnoSharedModule, RouterModule.forChild([SOLICITUD_ROUTE])],
    declarations: [SolicitudComponent],
    entryComponents: [],
    providers: [SolicitudService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SolicitudModule {}
