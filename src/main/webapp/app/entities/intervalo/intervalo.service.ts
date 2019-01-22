import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IIntervalo } from 'app/shared/model/intervalo.model';

type EntityResponseType = HttpResponse<IIntervalo>;
type EntityArrayResponseType = HttpResponse<IIntervalo[]>;

@Injectable({ providedIn: 'root' })
export class IntervaloService {
    public resourceUrl = SERVER_API_URL + 'api/intervalos';

    constructor(private http: HttpClient) {}

    create(intervalo: IIntervalo): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(intervalo);
        return this.http
            .post<IIntervalo>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(intervalo: IIntervalo): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(intervalo);
        return this.http
            .put<IIntervalo>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IIntervalo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IIntervalo[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(intervalo: IIntervalo): IIntervalo {
        const copy: IIntervalo = Object.assign({}, intervalo, {
            fechaHoraDesde:
                intervalo.fechaHoraDesde != null && intervalo.fechaHoraDesde.isValid() ? intervalo.fechaHoraDesde.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.fechaHoraDesde = res.body.fechaHoraDesde != null ? moment(res.body.fechaHoraDesde) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((intervalo: IIntervalo) => {
                intervalo.fechaHoraDesde = intervalo.fechaHoraDesde != null ? moment(intervalo.fechaHoraDesde) : null;
            });
        }
        return res;
    }
}
