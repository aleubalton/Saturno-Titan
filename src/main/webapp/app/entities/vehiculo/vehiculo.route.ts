import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Vehiculo } from 'app/shared/model/vehiculo.model';
import { VehiculoService } from './vehiculo.service';
import { VehiculoComponent } from './vehiculo.component';
import { VehiculoDetailComponent } from './vehiculo-detail.component';
import { VehiculoUpdateComponent } from './vehiculo-update.component';
import { VehiculoDeletePopupComponent } from './vehiculo-delete-dialog.component';
import { IVehiculo } from 'app/shared/model/vehiculo.model';

@Injectable({ providedIn: 'root' })
export class VehiculoResolve implements Resolve<IVehiculo> {
    constructor(private service: VehiculoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Vehiculo> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Vehiculo>) => response.ok),
                map((vehiculo: HttpResponse<Vehiculo>) => vehiculo.body)
            );
        }
        return of(new Vehiculo());
    }
}

export const vehiculoRoute: Routes = [
    {
        path: 'vehiculo',
        component: VehiculoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'saturnoApp.vehiculo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vehiculo/:id/view',
        component: VehiculoDetailComponent,
        resolve: {
            vehiculo: VehiculoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.vehiculo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vehiculo/new',
        component: VehiculoUpdateComponent,
        resolve: {
            vehiculo: VehiculoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.vehiculo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vehiculo/:id/edit',
        component: VehiculoUpdateComponent,
        resolve: {
            vehiculo: VehiculoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.vehiculo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const vehiculoPopupRoute: Routes = [
    {
        path: 'vehiculo/:id/delete',
        component: VehiculoDeletePopupComponent,
        resolve: {
            vehiculo: VehiculoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saturnoApp.vehiculo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
