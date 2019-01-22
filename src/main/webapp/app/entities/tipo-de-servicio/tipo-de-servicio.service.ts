import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITipoDeServicio } from 'app/shared/model/tipo-de-servicio.model';

type EntityResponseType = HttpResponse<ITipoDeServicio>;
type EntityArrayResponseType = HttpResponse<ITipoDeServicio[]>;

@Injectable({ providedIn: 'root' })
export class TipoDeServicioService {
    public resourceUrl = SERVER_API_URL + 'api/tipo-de-servicios';

    constructor(private http: HttpClient) {}

    create(tipoDeServicio: ITipoDeServicio): Observable<EntityResponseType> {
        return this.http.post<ITipoDeServicio>(this.resourceUrl, tipoDeServicio, { observe: 'response' });
    }

    update(tipoDeServicio: ITipoDeServicio): Observable<EntityResponseType> {
        return this.http.put<ITipoDeServicio>(this.resourceUrl, tipoDeServicio, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITipoDeServicio>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITipoDeServicio[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
