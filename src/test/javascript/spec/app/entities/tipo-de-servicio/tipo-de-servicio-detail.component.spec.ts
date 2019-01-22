/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaturnoTestModule } from '../../../test.module';
import { TipoDeServicioDetailComponent } from 'app/entities/tipo-de-servicio/tipo-de-servicio-detail.component';
import { TipoDeServicio } from 'app/shared/model/tipo-de-servicio.model';

describe('Component Tests', () => {
    describe('TipoDeServicio Management Detail Component', () => {
        let comp: TipoDeServicioDetailComponent;
        let fixture: ComponentFixture<TipoDeServicioDetailComponent>;
        const route = ({ data: of({ tipoDeServicio: new TipoDeServicio(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SaturnoTestModule],
                declarations: [TipoDeServicioDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TipoDeServicioDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TipoDeServicioDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.tipoDeServicio).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
