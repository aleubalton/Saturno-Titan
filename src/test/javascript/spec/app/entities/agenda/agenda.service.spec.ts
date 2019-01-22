/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { AgendaService } from 'app/entities/agenda/agenda.service';
import { IAgenda, Agenda, TipoRecurso } from 'app/shared/model/agenda.model';

describe('Service Tests', () => {
    describe('Agenda Service', () => {
        let injector: TestBed;
        let service: AgendaService;
        let httpMock: HttpTestingController;
        let elemDefault: IAgenda;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(AgendaService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Agenda(0, 'AAAAAAA', TipoRecurso.BAHIA, currentDate, currentDate, false);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        fechaDesde: currentDate.format(DATE_FORMAT),
                        fechaHasta: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Agenda', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        fechaDesde: currentDate.format(DATE_FORMAT),
                        fechaHasta: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        fechaDesde: currentDate,
                        fechaHasta: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Agenda(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Agenda', async () => {
                const returnedFromService = Object.assign(
                    {
                        nombre: 'BBBBBB',
                        tipoDeRecurso: 'BBBBBB',
                        fechaDesde: currentDate.format(DATE_FORMAT),
                        fechaHasta: currentDate.format(DATE_FORMAT),
                        activa: true
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        fechaDesde: currentDate,
                        fechaHasta: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Agenda', async () => {
                const returnedFromService = Object.assign(
                    {
                        nombre: 'BBBBBB',
                        tipoDeRecurso: 'BBBBBB',
                        fechaDesde: currentDate.format(DATE_FORMAT),
                        fechaHasta: currentDate.format(DATE_FORMAT),
                        activa: true
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        fechaDesde: currentDate,
                        fechaHasta: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Agenda', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
