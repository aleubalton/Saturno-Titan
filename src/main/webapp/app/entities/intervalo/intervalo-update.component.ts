import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IIntervalo } from 'app/shared/model/intervalo.model';
import { IntervaloService } from './intervalo.service';
import { IAgenda } from 'app/shared/model/agenda.model';
import { AgendaService } from 'app/entities/agenda';

@Component({
    selector: 'jhi-intervalo-update',
    templateUrl: './intervalo-update.component.html'
})
export class IntervaloUpdateComponent implements OnInit {
    intervalo: IIntervalo;
    isSaving: boolean;

    agenda: IAgenda[];
    fechaHoraDesde: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private intervaloService: IntervaloService,
        private agendaService: AgendaService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ intervalo }) => {
            this.intervalo = intervalo;
            this.fechaHoraDesde = this.intervalo.fechaHoraDesde != null ? this.intervalo.fechaHoraDesde.format(DATE_TIME_FORMAT) : null;
        });
        this.agendaService.query().subscribe(
            (res: HttpResponse<IAgenda[]>) => {
                this.agenda = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.intervalo.fechaHoraDesde = this.fechaHoraDesde != null ? moment(this.fechaHoraDesde, DATE_TIME_FORMAT) : null;
        if (this.intervalo.id !== undefined) {
            this.subscribeToSaveResponse(this.intervaloService.update(this.intervalo));
        } else {
            this.subscribeToSaveResponse(this.intervaloService.create(this.intervalo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IIntervalo>>) {
        result.subscribe((res: HttpResponse<IIntervalo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackAgendaById(index: number, item: IAgenda) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
