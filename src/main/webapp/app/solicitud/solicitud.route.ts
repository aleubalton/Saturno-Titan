import { Route } from '@angular/router';

import { SolicitudComponent } from './';

export const SOLICITUD_ROUTE: Route = {
    path: 'solicitar-turno',
    component: SolicitudComponent,
    data: {
        authorities: [],
        pageTitle: 'solicitud.title'
    }
};
