/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TurnoService } from 'app/entities/turno/turno.service';
import { ITurno, Turno, Estado } from 'app/shared/model/turno.model';

describe('Service Tests', () => {
    describe('Turno Service', () => {
        let injector: TestBed;
        let service: TurnoService;
        let httpMock: HttpTestingController;
        let elemDefault: ITurno;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(TurnoService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Turno(0, currentDate, Estado.RESERVADO);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        fechaHora: currentDate.format(DATE_TIME_FORMAT)
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

            it('should create a Turno', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        fechaHora: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        fechaHora: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Turno(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Turno', async () => {
                const returnedFromService = Object.assign(
                    {
                        fechaHora: currentDate.format(DATE_TIME_FORMAT),
                        estado: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        fechaHora: currentDate
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

            it('should return a list of Turno', async () => {
                const returnedFromService = Object.assign(
                    {
                        fechaHora: currentDate.format(DATE_TIME_FORMAT),
                        estado: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        fechaHora: currentDate
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

            it('should delete a Turno', async () => {
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
