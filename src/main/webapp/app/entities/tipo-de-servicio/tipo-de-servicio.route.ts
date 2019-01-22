import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TipoDeServicio } from 'app/shared/model/tipo-de-servicio.model';
import { TipoDeServicioService } from './tipo-de-servicio.service';
import { TipoDeServicioComponent } from './tipo-de-servicio.component';
import { TipoDeServicioDetailComponent } from './tipo-de-servicio-detail.component';
import { TipoDeServicioUpdateComponent } from './tipo-de-servicio-update.component';
import { TipoDeServicioDeletePopupComponent } from './tipo-de-servicio-delete-dialog.component';
import { ITipoDeServicio } from 'app/shared/model/tipo-de-servicio.model';

@Injectable({ providedIn: 'root' })
export class TipoDeServicioResolve implements Resolve<ITipoDeServicio> {
    constructor(private service: TipoDeServicioService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<TipoDeServicio> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TipoDeServicio>) => response.ok),
                map((tipoDeServicio: HttpResponse<TipoDeServicio>) => tipoDeServicio.body)
            );
        }
        return of(new TipoDeServicio());
    }
}

export const tipoDeServicioRoute: Routes = [
    {
        path: 'tipo-de-servicio',
        component: TipoDeServicioComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'saturnoApp.tipoDeServicio.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-de-servicio/:id/view',
        component: TipoDeServicioDetailComponent,
        resolve: {
            tipoDeServicio: TipoDeServicioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.tipoDeServicio.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-de-servicio/new',
        component: TipoDeServicioUpdateComponent,
        resolve: {
            tipoDeServicio: TipoDeServicioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.tipoDeServicio.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-de-servicio/:id/edit',
        component: TipoDeServicioUpdateComponent,
        resolve: {
            tipoDeServicio: TipoDeServicioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.tipoDeServicio.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tipoDeServicioPopupRoute: Routes = [
    {
        path: 'tipo-de-servicio/:id/delete',
        component: TipoDeServicioDeletePopupComponent,
        resolve: {
            tipoDeServicio: TipoDeServicioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.tipoDeServicio.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
