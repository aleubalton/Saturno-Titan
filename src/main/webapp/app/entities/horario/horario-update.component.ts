import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IHorario } from 'app/shared/model/horario.model';
import { HorarioService } from './horario.service';
import { IAgenda } from 'app/shared/model/agenda.model';
import { AgendaService } from 'app/entities/agenda';

@Component({
    selector: 'jhi-horario-update',
    templateUrl: './horario-update.component.html'
})
export class HorarioUpdateComponent implements OnInit {
    horario: IHorario;
    isSaving: boolean;

    agenda: IAgenda[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private horarioService: HorarioService,
        private agendaService: AgendaService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ horario }) => {
            this.horario = horario;
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
        if (this.horario.id !== undefined) {
            this.subscribeToSaveResponse(this.horarioService.update(this.horario));
        } else {
            this.subscribeToSaveResponse(this.horarioService.create(this.horario));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IHorario>>) {
        result.subscribe((res: HttpResponse<IHorario>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
