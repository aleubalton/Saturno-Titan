import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Intervalo } from 'app/shared/model/intervalo.model';
import { IntervaloService } from './intervalo.service';
import { IntervaloComponent } from './intervalo.component';
import { IntervaloDetailComponent } from './intervalo-detail.component';
import { IntervaloUpdateComponent } from './intervalo-update.component';
import { IntervaloDeletePopupComponent } from './intervalo-delete-dialog.component';
import { IIntervalo } from 'app/shared/model/intervalo.model';

@Injectable({ providedIn: 'root' })
export class IntervaloResolve implements Resolve<IIntervalo> {
    constructor(private service: IntervaloService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Intervalo> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Intervalo>) => response.ok),
                map((intervalo: HttpResponse<Intervalo>) => intervalo.body)
            );
        }
        return of(new Intervalo());
    }
}

export const intervaloRoute: Routes = [
    {
        path: 'intervalo',
        component: IntervaloComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'saturnoApp.intervalo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'intervalo/:id/view',
        component: IntervaloDetailComponent,
        resolve: {
            intervalo: IntervaloResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.intervalo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'intervalo/new',
        component: IntervaloUpdateComponent,
        resolve: {
            intervalo: IntervaloResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.intervalo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'intervalo/:id/edit',
        component: IntervaloUpdateComponent,
        resolve: {
            intervalo: IntervaloResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.intervalo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const intervaloPopupRoute: Routes = [
    {
        path: 'intervalo/:id/delete',
        component: IntervaloDeletePopupComponent,
        resolve: {
            intervalo: IntervaloResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.intervalo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
