import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPrecioServicio } from 'app/shared/model/precio-servicio.model';

type EntityResponseType = HttpResponse<IPrecioServicio>;
type EntityArrayResponseType = HttpResponse<IPrecioServicio[]>;

@Injectable({ providedIn: 'root' })
export class PrecioServicioService {
    public resourceUrl = SERVER_API_URL + 'api/precio-servicios';

    constructor(private http: HttpClient) {}

    create(precioServicio: IPrecioServicio): Observable<EntityResponseType> {
        return this.http.post<IPrecioServicio>(this.resourceUrl, precioServicio, { observe: 'response' });
    }

    update(precioServicio: IPrecioServicio): Observable<EntityResponseType> {
        return this.http.put<IPrecioServicio>(this.resourceUrl, precioServicio, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPrecioServicio>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPrecioServicio[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
