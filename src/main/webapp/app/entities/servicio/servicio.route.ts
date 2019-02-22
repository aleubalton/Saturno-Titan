import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Servicio } from 'app/shared/model/servicio.model';
import { ServicioService } from './servicio.service';
import { ServicioComponent } from './servicio.component';
import { ServicioDetailComponent } from './servicio-detail.component';
import { ServicioUpdateComponent } from './servicio-update.component';
import { ServicioDeletePopupComponent } from './servicio-delete-dialog.component';
import { IServicio } from 'app/shared/model/servicio.model';

@Injectable({ providedIn: 'root' })
export class ServicioResolve implements Resolve<IServicio> {
    constructor(private service: ServicioService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Servicio> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Servicio>) => response.ok),
                map((servicio: HttpResponse<Servicio>) => servicio.body)
            );
        }
        return of(new Servicio());
    }
}

export const servicioRoute: Routes = [
    {
        path: 'servicio',
        component: ServicioComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'saturnoApp.servicio.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'servicio/:id/view',
        component: ServicioDetailComponent,
        resolve: {
            servicio: ServicioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.servicio.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'servicio/new',
        component: ServicioUpdateComponent,
        resolve: {
            servicio: ServicioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.servicio.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'servicio/:id/edit',
        component: ServicioUpdateComponent,
        resolve: {
            servicio: ServicioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.servicio.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const servicioPopupRoute: Routes = [
    {
        path: 'servicio/:id/delete',
        component: ServicioDeletePopupComponent,
        resolve: {
            servicio: ServicioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.servicio.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
