import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDiaNoLaborable } from 'app/shared/model/dia-no-laborable.model';

type EntityResponseType = HttpResponse<IDiaNoLaborable>;
type EntityArrayResponseType = HttpResponse<IDiaNoLaborable[]>;

@Injectable({ providedIn: 'root' })
export class DiaNoLaborableService {
    public resourceUrl = SERVER_API_URL + 'api/dia-no-laborables';

    constructor(private http: HttpClient) {}

    create(diaNoLaborable: IDiaNoLaborable): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(diaNoLaborable);
        return this.http
            .post<IDiaNoLaborable>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(diaNoLaborable: IDiaNoLaborable): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(diaNoLaborable);
        return this.http
            .put<IDiaNoLaborable>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IDiaNoLaborable>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IDiaNoLaborable[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(diaNoLaborable: IDiaNoLaborable): IDiaNoLaborable {
        const copy: IDiaNoLaborable = Object.assign({}, diaNoLaborable, {
            fecha: diaNoLaborable.fecha != null && diaNoLaborable.fecha.isValid() ? diaNoLaborable.fecha.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.fecha = res.body.fecha != null ? moment(res.body.fecha) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((diaNoLaborable: IDiaNoLaborable) => {
                diaNoLaborable.fecha = diaNoLaborable.fecha != null ? moment(diaNoLaborable.fecha) : null;
            });
        }
        return res;
    }
}
