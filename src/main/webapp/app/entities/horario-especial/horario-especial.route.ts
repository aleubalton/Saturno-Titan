import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HorarioEspecial } from 'app/shared/model/horario-especial.model';
import { HorarioEspecialService } from './horario-especial.service';
import { HorarioEspecialComponent } from './horario-especial.component';
import { HorarioEspecialDetailComponent } from './horario-especial-detail.component';
import { HorarioEspecialUpdateComponent } from './horario-especial-update.component';
import { HorarioEspecialDeletePopupComponent } from './horario-especial-delete-dialog.component';
import { IHorarioEspecial } from 'app/shared/model/horario-especial.model';

@Injectable({ providedIn: 'root' })
export class HorarioEspecialResolve implements Resolve<IHorarioEspecial> {
    constructor(private service: HorarioEspecialService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<HorarioEspecial> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<HorarioEspecial>) => response.ok),
                map((horarioEspecial: HttpResponse<HorarioEspecial>) => horarioEspecial.body)
            );
        }
        return of(new HorarioEspecial());
    }
}

export const horarioEspecialRoute: Routes = [
    {
        path: 'horario-especial',
        component: HorarioEspecialComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'saturnoApp.horarioEspecial.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'horario-especial/:id/view',
        component: HorarioEspecialDetailComponent,
        resolve: {
            horarioEspecial: HorarioEspecialResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.horarioEspecial.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'horario-especial/new',
        component: HorarioEspecialUpdateComponent,
        resolve: {
            horarioEspecial: HorarioEspecialResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.horarioEspecial.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'horario-especial/:id/edit',
        component: HorarioEspecialUpdateComponent,
        resolve: {
            horarioEspecial: HorarioEspecialResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.horarioEspecial.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const horarioEspecialPopupRoute: Routes = [
    {
        path: 'horario-especial/:id/delete',
        component: HorarioEspecialDeletePopupComponent,
        resolve: {
            horarioEspecial: HorarioEspecialResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.horarioEspecial.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
