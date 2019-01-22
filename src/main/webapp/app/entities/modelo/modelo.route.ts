import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Modelo } from 'app/shared/model/modelo.model';
import { ModeloService } from './modelo.service';
import { ModeloComponent } from './modelo.component';
import { ModeloDetailComponent } from './modelo-detail.component';
import { ModeloUpdateComponent } from './modelo-update.component';
import { ModeloDeletePopupComponent } from './modelo-delete-dialog.component';
import { IModelo } from 'app/shared/model/modelo.model';

@Injectable({ providedIn: 'root' })
export class ModeloResolve implements Resolve<IModelo> {
    constructor(private service: ModeloService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Modelo> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Modelo>) => response.ok),
                map((modelo: HttpResponse<Modelo>) => modelo.body)
            );
        }
        return of(new Modelo());
    }
}

export const modeloRoute: Routes = [
    {
        path: 'modelo',
        component: ModeloComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'saturnoApp.modelo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'modelo/:id/view',
        component: ModeloDetailComponent,
        resolve: {
            modelo: ModeloResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.modelo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'modelo/new',
        component: ModeloUpdateComponent,
        resolve: {
            modelo: ModeloResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.modelo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'modelo/:id/edit',
        component: ModeloUpdateComponent,
        resolve: {
            modelo: ModeloResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.modelo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const modeloPopupRoute: Routes = [
    {
        path: 'modelo/:id/delete',
        component: ModeloDeletePopupComponent,
        resolve: {
            modelo: ModeloResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.modelo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
