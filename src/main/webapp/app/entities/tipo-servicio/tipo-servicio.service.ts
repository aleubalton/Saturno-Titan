import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITipoServicio } from 'app/shared/model/tipo-servicio.model';

type EntityResponseType = HttpResponse<ITipoServicio>;
type EntityArrayResponseType = HttpResponse<ITipoServicio[]>;

@Injectable({ providedIn: 'root' })
export class TipoServicioService {
    public resourceUrl = SERVER_API_URL + 'api/tipo-servicios';

    constructor(private http: HttpClient) {}

    create(tipoServicio: ITipoServicio): Observable<EntityResponseType> {
        return this.http.post<ITipoServicio>(this.resourceUrl, tipoServicio, { observe: 'response' });
    }

    update(tipoServicio: ITipoServicio): Observable<EntityResponseType> {
        return this.http.put<ITipoServicio>(this.resourceUrl, tipoServicio, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITipoServicio>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITipoServicio[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
