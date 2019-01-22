/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IntervaloService } from 'app/entities/intervalo/intervalo.service';
import { IIntervalo, Intervalo, Dia } from 'app/shared/model/intervalo.model';

describe('Service Tests', () => {
    describe('Intervalo Service', () => {
        let injector: TestBed;
        let service: IntervaloService;
        let httpMock: HttpTestingController;
        let elemDefault: IIntervalo;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(IntervaloService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Intervalo(0, currentDate, 0, Dia.LUNES, false);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        fechaHoraDesde: currentDate.format(DATE_TIME_FORMAT)
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

            it('should create a Intervalo', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        fechaHoraDesde: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        fechaHoraDesde: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Intervalo(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Intervalo', async () => {
                const returnedFromService = Object.assign(
                    {
                        fechaHoraDesde: currentDate.format(DATE_TIME_FORMAT),
                        duracion: 1,
                        dia: 'BBBBBB',
                        repite: true
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        fechaHoraDesde: currentDate
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

            it('should return a list of Intervalo', async () => {
                const returnedFromService = Object.assign(
                    {
                        fechaHoraDesde: currentDate.format(DATE_TIME_FORMAT),
                        duracion: 1,
                        dia: 'BBBBBB',
                        repite: true
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        fechaHoraDesde: currentDate
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

            it('should delete a Intervalo', async () => {
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
