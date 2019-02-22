import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITarea } from 'app/shared/model/tarea.model';

type EntityResponseType = HttpResponse<ITarea>;
type EntityArrayResponseType = HttpResponse<ITarea[]>;

@Injectable({ providedIn: 'root' })
export class TareaService {
    public resourceUrl = SERVER_API_URL + 'api/tareas';

    constructor(private http: HttpClient) {}

    create(tarea: ITarea): Observable<EntityResponseType> {
        return this.http.post<ITarea>(this.resourceUrl, tarea, { observe: 'response' });
    }

    update(tarea: ITarea): Observable<EntityResponseType> {
        return this.http.put<ITarea>(this.resourceUrl, tarea, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITarea>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITarea[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
