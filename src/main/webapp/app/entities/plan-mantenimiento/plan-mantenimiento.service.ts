import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPlanMantenimiento } from 'app/shared/model/plan-mantenimiento.model';

type EntityResponseType = HttpResponse<IPlanMantenimiento>;
type EntityArrayResponseType = HttpResponse<IPlanMantenimiento[]>;

@Injectable({ providedIn: 'root' })
export class PlanMantenimientoService {
    public resourceUrl = SERVER_API_URL + 'api/plan-mantenimientos';

    constructor(private http: HttpClient) {}

    create(planMantenimiento: IPlanMantenimiento): Observable<EntityResponseType> {
        return this.http.post<IPlanMantenimiento>(this.resourceUrl, planMantenimiento, { observe: 'response' });
    }

    update(planMantenimiento: IPlanMantenimiento): Observable<EntityResponseType> {
        return this.http.put<IPlanMantenimiento>(this.resourceUrl, planMantenimiento, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPlanMantenimiento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPlanMantenimiento[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
