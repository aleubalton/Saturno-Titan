import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IAgenda } from 'app/shared/model/agenda.model';
import { AgendaService } from './agenda.service';
import { IHorario } from 'app/shared/model/horario.model';
import { HorarioService } from 'app/entities/horario';
import { IHorarioEspecial } from 'app/shared/model/horario-especial.model';
import { HorarioEspecialService } from 'app/entities/horario-especial';

@Component({
    selector: 'jhi-agenda-update',
    templateUrl: './agenda-update.component.html'
})
export class AgendaUpdateComponent implements OnInit {
    agenda: IAgenda;
    isSaving: boolean;

    horarios: IHorario[];
    horariosSelected: any[];
    horarioEspecialsSelected: any[];

    horarioespecials: IHorarioEspecial[];

    fechaDesdeDp: any;
    fechaHastaDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private agendaService: AgendaService,
        private horarioService: HorarioService,
        private horarioEspecialService: HorarioEspecialService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ agenda }) => {
            this.agenda = agenda;
            this.horarioService.query().subscribe(
                (res: HttpResponse<IHorario[]>) => {
                    this.horarios = res.body;
                    this.horariosSelected = this.horarios.map(h => {
                        const rObj = { id: h.id, descripcion: h.descripcion, enabled: agenda.horarios.some(ah => ah.id === h.id) };
                        return rObj;
                    });
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
            this.horarioEspecialService.query().subscribe(
                (res: HttpResponse<IHorarioEspecial[]>) => {
                    this.horarioespecials = res.body;
                    this.horarioEspecialsSelected = this.horarioespecials.map(h => {
                        const rObj = { id: h.id, descripcion: h.descripcion, enabled: agenda.horarioEspecials.some(ah => ah.id === h.id) };
                        return rObj;
                    });
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.agenda.horarios = this.horariosSelected.filter(h => h.enabled);
        this.agenda.horarioEspecials = this.horarioEspecialsSelected.filter(h => h.enabled);
        if (this.agenda.id !== undefined) {
            this.subscribeToSaveResponse(this.agendaService.update(this.agenda));
        } else {
            this.subscribeToSaveResponse(this.agendaService.create(this.agenda));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAgenda>>) {
        result.subscribe((res: HttpResponse<IAgenda>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackHorarioById(index: number, item: IHorario) {
        return item.id;
    }

    trackHorarioEspecialById(index: number, item: IHorarioEspecial) {
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
