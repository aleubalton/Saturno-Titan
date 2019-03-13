import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PlanMantenimiento } from 'app/shared/model/plan-mantenimiento.model';
import { PlanMantenimientoService } from './plan-mantenimiento.service';
import { PlanMantenimientoComponent } from './plan-mantenimiento.component';
import { PlanMantenimientoDetailComponent } from './plan-mantenimiento-detail.component';
import { PlanMantenimientoUpdateComponent } from './plan-mantenimiento-update.component';
import { PlanMantenimientoDeletePopupComponent } from './plan-mantenimiento-delete-dialog.component';
import { IPlanMantenimiento } from 'app/shared/model/plan-mantenimiento.model';

@Injectable({ providedIn: 'root' })
export class PlanMantenimientoResolve implements Resolve<IPlanMantenimiento> {
    constructor(private service: PlanMantenimientoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<PlanMantenimiento> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PlanMantenimiento>) => response.ok),
                map((planMantenimiento: HttpResponse<PlanMantenimiento>) => planMantenimiento.body)
            );
        }
        return of(new PlanMantenimiento());
    }
}

export const planMantenimientoRoute: Routes = [
    {
        path: 'plan-mantenimiento',
        component: PlanMantenimientoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'saturnoApp.planMantenimiento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'plan-mantenimiento/:id/view',
        component: PlanMantenimientoDetailComponent,
        resolve: {
            planMantenimiento: PlanMantenimientoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.planMantenimiento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'plan-mantenimiento/new',
        component: PlanMantenimientoUpdateComponent,
        resolve: {
            planMantenimiento: PlanMantenimientoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.planMantenimiento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'plan-mantenimiento/:id/edit',
        component: PlanMantenimientoUpdateComponent,
        resolve: {
            planMantenimiento: PlanMantenimientoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.planMantenimiento.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const planMantenimientoPopupRoute: Routes = [
    {
        path: 'plan-mantenimiento/:id/delete',
        component: PlanMantenimientoDeletePopupComponent,
        resolve: {
            planMantenimiento: PlanMantenimientoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.planMantenimiento.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
