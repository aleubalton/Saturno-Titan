import { Component, OnInit, OnDestroy, ViewChild, TemplateRef } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ITurno } from 'app/shared/model/turno.model';
import { Principal } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { TurnoService } from './turno.service';

import { CalendarEvent, CalendarEventTitleFormatter } from 'angular-calendar';
import { CustomEventTitleFormatter } from './disable-tooltip.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { startOfDay, endOfDay, subDays, addDays, endOfMonth, isSameDay, isSameMonth, addHours } from 'date-fns';

const colors: any[] = [
    {},
    {
        primary: '#ad2121',
        secondary: '#FAE3E3'
    },
    {
        primary: '#1e90ff',
        secondary: '#D1E8FF'
    },
    {
        primary: '#e3bc08',
        secondary: '#FDF1BA'
    }
];

@Component({
    selector: 'jhi-calendario',
    templateUrl: './calendario.component.html',
    providers: [
        {
            provide: CalendarEventTitleFormatter,
            useClass: CustomEventTitleFormatter
        }
    ]
})
export class CalendarioComponent implements OnInit, OnDestroy {
    @ViewChild('modalContent')
    modalContent: TemplateRef<any>;
    turno: ITurno;
    currentAccount: any;
    turnos: ITurno[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;

    viewDate: Date = new Date();

    events: CalendarEvent[];

    constructor(
        private modal: NgbModal,
        private turnoService: TurnoService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
    }

    loadAll() {
        this.turnoService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<ITurno[]>) => this.paginateTurnos(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/turno'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate([
            '/turno',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTurnos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITurno) {
        return item.id;
    }

    registerChangeInTurnos() {
        this.eventSubscriber = this.eventManager.subscribe('turnoListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private paginateTurnos(data: ITurno[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.queryCount = this.totalItems;
        this.turnos = data;
        this.events = this.turnos.filter(turno => turno.estado !== 'EXPIRADO' && turno.estado !== 'CANCELADO').map((turno: ITurno) => {
            return {
                title: turno.vehiculoPatente,
                start: addHours(turno.fechaHora.toDate(), 0),
                end: addHours(turno.fechaHora.toDate(), turno.duracion / 60),
                color: colors[turno.agendaId],
                // allDay: true,
                meta: {
                    turno
                }
            };
        });
    }

    public eventClicked({ event }: { event: CalendarEvent }): void {
        // console.log('Event clicked', event);
        // this.router.navigate(['/turno', event.meta.turno.id, 'view']);
        this.turno = event.meta.turno;
        this.modal.open(this.modalContent, { size: 'sm' });
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
