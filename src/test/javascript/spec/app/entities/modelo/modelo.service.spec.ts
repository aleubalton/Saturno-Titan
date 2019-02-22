/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { ModeloService } from 'app/entities/modelo/modelo.service';
import { IModelo, Modelo, Marca, Categoria } from 'app/shared/model/modelo.model';

describe('Service Tests', () => {
    describe('Modelo Service', () => {
        let injector: TestBed;
        let service: ModeloService;
        let httpMock: HttpTestingController;
        let elemDefault: IModelo;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(ModeloService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new Modelo(0, 'AAAAAAA', 'AAAAAAA', 0, 0, Marca.TOYOTA, Categoria.AUTOMOVIL);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Modelo', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new Modelo(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Modelo', async () => {
                const returnedFromService = Object.assign(
                    {
                        codigo: 'BBBBBB',
                        nombre: 'BBBBBB',
                        anioInicioProduccion: 1,
                        anioFinProduccion: 1,
                        marca: 'BBBBBB',
                        categoria: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Modelo', async () => {
                const returnedFromService = Object.assign(
                    {
                        codigo: 'BBBBBB',
                        nombre: 'BBBBBB',
                        anioInicioProduccion: 1,
                        anioFinProduccion: 1,
                        marca: 'BBBBBB',
                        categoria: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
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

            it('should delete a Modelo', async () => {
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
