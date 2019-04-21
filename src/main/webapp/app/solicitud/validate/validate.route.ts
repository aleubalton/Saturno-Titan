import { Route } from '@angular/router';

import { ValidateComponent } from './validate.component';

export const VALIDATE_ROUTE: Route = {
    path: 'validateTurno',
    component: ValidateComponent,
    data: {
        authorities: [],
        pageTitle: 'validate.title'
    }
};
