import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IHorarioEspecial } from 'app/shared/model/horario-especial.model';
import { HorarioEspecialService } from './horario-especial.service';
import { IAgenda } from 'app/shared/model/agenda.model';
import { AgendaService } from 'app/entities/agenda';

@Component({
    selector: 'jhi-horario-especial-update',
    templateUrl: './horario-especial-update.component.html'
})
export class HorarioEspecialUpdateComponent implements OnInit {
    horarioEspecial: IHorarioEspecial;
    isSaving: boolean;

    agenda: IAgenda[];
    fechaDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private horarioEspecialService: HorarioEspecialService,
        private agendaService: AgendaService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ horarioEspecial }) => {
            this.horarioEspecial = horarioEspecial;
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
        if (this.horarioEspecial.id !== undefined) {
            this.subscribeToSaveResponse(this.horarioEspecialService.update(this.horarioEspecial));
        } else {
            this.subscribeToSaveResponse(this.horarioEspecialService.create(this.horarioEspecial));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IHorarioEspecial>>) {
        result.subscribe((res: HttpResponse<IHorarioEspecial>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
