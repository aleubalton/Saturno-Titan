import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Tarea } from 'app/shared/model/tarea.model';
import { TareaService } from './tarea.service';
import { TareaComponent } from './tarea.component';
import { TareaDetailComponent } from './tarea-detail.component';
import { TareaUpdateComponent } from './tarea-update.component';
import { TareaDeletePopupComponent } from './tarea-delete-dialog.component';
import { ITarea } from 'app/shared/model/tarea.model';

@Injectable({ providedIn: 'root' })
export class TareaResolve implements Resolve<ITarea> {
    constructor(private service: TareaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Tarea> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Tarea>) => response.ok),
                map((tarea: HttpResponse<Tarea>) => tarea.body)
            );
        }
        return of(new Tarea());
    }
}

export const tareaRoute: Routes = [
    {
        path: 'tarea',
        component: TareaComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'saturnoApp.tarea.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tarea/:id/view',
        component: TareaDetailComponent,
        resolve: {
            tarea: TareaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.tarea.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tarea/new',
        component: TareaUpdateComponent,
        resolve: {
            tarea: TareaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.tarea.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tarea/:id/edit',
        component: TareaUpdateComponent,
        resolve: {
            tarea: TareaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.tarea.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tareaPopupRoute: Routes = [
    {
        path: 'tarea/:id/delete',
        component: TareaDeletePopupComponent,
        resolve: {
            tarea: TareaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.tarea.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
