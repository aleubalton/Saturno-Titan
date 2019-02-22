import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IHorarioEspecial } from 'app/shared/model/horario-especial.model';

type EntityResponseType = HttpResponse<IHorarioEspecial>;
type EntityArrayResponseType = HttpResponse<IHorarioEspecial[]>;

@Injectable({ providedIn: 'root' })
export class HorarioEspecialService {
    public resourceUrl = SERVER_API_URL + 'api/horario-especials';

    constructor(private http: HttpClient) {}

    create(horarioEspecial: IHorarioEspecial): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(horarioEspecial);
        return this.http
            .post<IHorarioEspecial>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(horarioEspecial: IHorarioEspecial): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(horarioEspecial);
        return this.http
            .put<IHorarioEspecial>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IHorarioEspecial>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IHorarioEspecial[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(horarioEspecial: IHorarioEspecial): IHorarioEspecial {
        const copy: IHorarioEspecial = Object.assign({}, horarioEspecial, {
            fecha: horarioEspecial.fecha != null && horarioEspecial.fecha.isValid() ? horarioEspecial.fecha.format(DATE_FORMAT) : null
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
            res.body.forEach((horarioEspecial: IHorarioEspecial) => {
                horarioEspecial.fecha = horarioEspecial.fecha != null ? moment(horarioEspecial.fecha) : null;
            });
        }
        return res;
    }
}
