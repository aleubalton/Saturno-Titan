import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITurno } from 'app/shared/model/turno.model';

type EntityResponseType = HttpResponse<ITurno>;
type EntityArrayResponseType = HttpResponse<ITurno[]>;

@Injectable({ providedIn: 'root' })
export class TurnoService {
    public resourceUrl = SERVER_API_URL + 'api/turnos';

    constructor(private http: HttpClient) {}

    create(turno: ITurno): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(turno);
        return this.http
            .post<ITurno>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(turno: ITurno): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(turno);
        return this.http
            .put<ITurno>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ITurno>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ITurno[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    queryByFecha(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ITurno[]>(`${this.resourceUrl}ByFecha`, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(turno: ITurno): ITurno {
        const copy: ITurno = Object.assign({}, turno, {
            fechaHora: turno.fechaHora != null && turno.fechaHora.isValid() ? turno.fechaHora.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.fechaHora = res.body.fechaHora != null ? moment(res.body.fechaHora) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((turno: ITurno) => {
                turno.fechaHora = turno.fechaHora != null ? moment(turno.fechaHora) : null;
            });
        }
        return res;
    }
}
