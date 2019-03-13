import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TipoServicio } from 'app/shared/model/tipo-servicio.model';
import { TipoServicioService } from './tipo-servicio.service';
import { TipoServicioComponent } from './tipo-servicio.component';
import { TipoServicioDetailComponent } from './tipo-servicio-detail.component';
import { TipoServicioUpdateComponent } from './tipo-servicio-update.component';
import { TipoServicioDeletePopupComponent } from './tipo-servicio-delete-dialog.component';
import { ITipoServicio } from 'app/shared/model/tipo-servicio.model';

@Injectable({ providedIn: 'root' })
export class TipoServicioResolve implements Resolve<ITipoServicio> {
    constructor(private service: TipoServicioService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<TipoServicio> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TipoServicio>) => response.ok),
                map((tipoServicio: HttpResponse<TipoServicio>) => tipoServicio.body)
            );
        }
        return of(new TipoServicio());
    }
}

export const tipoServicioRoute: Routes = [
    {
        path: 'tipo-servicio',
        component: TipoServicioComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'saturnoApp.tipoServicio.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-servicio/:id/view',
        component: TipoServicioDetailComponent,
        resolve: {
            tipoServicio: TipoServicioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.tipoServicio.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-servicio/new',
        component: TipoServicioUpdateComponent,
        resolve: {
            tipoServicio: TipoServicioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.tipoServicio.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-servicio/:id/edit',
        component: TipoServicioUpdateComponent,
        resolve: {
            tipoServicio: TipoServicioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.tipoServicio.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tipoServicioPopupRoute: Routes = [
    {
        path: 'tipo-servicio/:id/delete',
        component: TipoServicioDeletePopupComponent,
        resolve: {
            tipoServicio: TipoServicioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.tipoServicio.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
