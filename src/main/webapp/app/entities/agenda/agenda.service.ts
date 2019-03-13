import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAgenda } from 'app/shared/model/agenda.model';

type EntityResponseType = HttpResponse<IAgenda>;
type EntityArrayResponseType = HttpResponse<IAgenda[]>;

@Injectable({ providedIn: 'root' })
export class AgendaService {
    public resourceUrl = SERVER_API_URL + 'api/agenda';

    constructor(private http: HttpClient) {}

    create(agenda: IAgenda): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(agenda);
        return this.http
            .post<IAgenda>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(agenda: IAgenda): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(agenda);
        return this.http
            .put<IAgenda>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IAgenda>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAgenda[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(agenda: IAgenda): IAgenda {
        const copy: IAgenda = Object.assign({}, agenda, {
            fechaDesde: agenda.fechaDesde != null && agenda.fechaDesde.isValid() ? agenda.fechaDesde.format(DATE_FORMAT) : null,
            fechaHasta: agenda.fechaHasta != null && agenda.fechaHasta.isValid() ? agenda.fechaHasta.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.fechaDesde = res.body.fechaDesde != null ? moment(res.body.fechaDesde) : null;
            res.body.fechaHasta = res.body.fechaHasta != null ? moment(res.body.fechaHasta) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((agenda: IAgenda) => {
                agenda.fechaDesde = agenda.fechaDesde != null ? moment(agenda.fechaDesde) : null;
                agenda.fechaHasta = agenda.fechaHasta != null ? moment(agenda.fechaHasta) : null;
            });
        }
        return res;
    }
}
