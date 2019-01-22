import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DiaNoLaborable } from 'app/shared/model/dia-no-laborable.model';
import { DiaNoLaborableService } from './dia-no-laborable.service';
import { DiaNoLaborableComponent } from './dia-no-laborable.component';
import { DiaNoLaborableDetailComponent } from './dia-no-laborable-detail.component';
import { DiaNoLaborableUpdateComponent } from './dia-no-laborable-update.component';
import { DiaNoLaborableDeletePopupComponent } from './dia-no-laborable-delete-dialog.component';
import { IDiaNoLaborable } from 'app/shared/model/dia-no-laborable.model';

@Injectable({ providedIn: 'root' })
export class DiaNoLaborableResolve implements Resolve<IDiaNoLaborable> {
    constructor(private service: DiaNoLaborableService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<DiaNoLaborable> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DiaNoLaborable>) => response.ok),
                map((diaNoLaborable: HttpResponse<DiaNoLaborable>) => diaNoLaborable.body)
            );
        }
        return of(new DiaNoLaborable());
    }
}

export const diaNoLaborableRoute: Routes = [
    {
        path: 'dia-no-laborable',
        component: DiaNoLaborableComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'saturnoApp.diaNoLaborable.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dia-no-laborable/:id/view',
        component: DiaNoLaborableDetailComponent,
        resolve: {
            diaNoLaborable: DiaNoLaborableResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.diaNoLaborable.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dia-no-laborable/new',
        component: DiaNoLaborableUpdateComponent,
        resolve: {
            diaNoLaborable: DiaNoLaborableResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.diaNoLaborable.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dia-no-laborable/:id/edit',
        component: DiaNoLaborableUpdateComponent,
        resolve: {
            diaNoLaborable: DiaNoLaborableResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.diaNoLaborable.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const diaNoLaborablePopupRoute: Routes = [
    {
        path: 'dia-no-laborable/:id/delete',
        component: DiaNoLaborableDeletePopupComponent,
        resolve: {
            diaNoLaborable: DiaNoLaborableResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.diaNoLaborable.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
