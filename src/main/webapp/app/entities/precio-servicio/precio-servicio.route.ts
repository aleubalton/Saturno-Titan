import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PrecioServicio } from 'app/shared/model/precio-servicio.model';
import { PrecioServicioService } from './precio-servicio.service';
import { PrecioServicioComponent } from './precio-servicio.component';
import { PrecioServicioDetailComponent } from './precio-servicio-detail.component';
import { PrecioServicioUpdateComponent } from './precio-servicio-update.component';
import { PrecioServicioDeletePopupComponent } from './precio-servicio-delete-dialog.component';
import { IPrecioServicio } from 'app/shared/model/precio-servicio.model';

@Injectable({ providedIn: 'root' })
export class PrecioServicioResolve implements Resolve<IPrecioServicio> {
    constructor(private service: PrecioServicioService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<PrecioServicio> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PrecioServicio>) => response.ok),
                map((precioServicio: HttpResponse<PrecioServicio>) => precioServicio.body)
            );
        }
        return of(new PrecioServicio());
    }
}

export const precioServicioRoute: Routes = [
    {
        path: 'precio-servicio',
        component: PrecioServicioComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'saturnoApp.precioServicio.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'precio-servicio/:id/view',
        component: PrecioServicioDetailComponent,
        resolve: {
            precioServicio: PrecioServicioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.precioServicio.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'precio-servicio/new',
        component: PrecioServicioUpdateComponent,
        resolve: {
            precioServicio: PrecioServicioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.precioServicio.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'precio-servicio/:id/edit',
        component: PrecioServicioUpdateComponent,
        resolve: {
            precioServicio: PrecioServicioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.precioServicio.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const precioServicioPopupRoute: Routes = [
    {
        path: 'precio-servicio/:id/delete',
        component: PrecioServicioDeletePopupComponent,
        resolve: {
            precioServicio: PrecioServicioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.precioServicio.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
