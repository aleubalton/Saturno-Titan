import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IDiaNoLaborable } from 'app/shared/model/dia-no-laborable.model';
import { DiaNoLaborableService } from './dia-no-laborable.service';
import { IAgenda } from 'app/shared/model/agenda.model';
import { AgendaService } from 'app/entities/agenda';

@Component({
    selector: 'jhi-dia-no-laborable-update',
    templateUrl: './dia-no-laborable-update.component.html'
})
export class DiaNoLaborableUpdateComponent implements OnInit {
    diaNoLaborable: IDiaNoLaborable;
    isSaving: boolean;

    agenda: IAgenda[];
    fechaDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private diaNoLaborableService: DiaNoLaborableService,
        private agendaService: AgendaService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ diaNoLaborable }) => {
            this.diaNoLaborable = diaNoLaborable;
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
        if (this.diaNoLaborable.id !== undefined) {
            this.subscribeToSaveResponse(this.diaNoLaborableService.update(this.diaNoLaborable));
        } else {
            this.subscribeToSaveResponse(this.diaNoLaborableService.create(this.diaNoLaborable));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDiaNoLaborable>>) {
        result.subscribe((res: HttpResponse<IDiaNoLaborable>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
