/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaturnoTestModule } from '../../../test.module';
import { TipoServicioDetailComponent } from 'app/entities/tipo-servicio/tipo-servicio-detail.component';
import { TipoServicio } from 'app/shared/model/tipo-servicio.model';

describe('Component Tests', () => {
    describe('TipoServicio Management Detail Component', () => {
        let comp: TipoServicioDetailComponent;
        let fixture: ComponentFixture<TipoServicioDetailComponent>;
        const route = ({ data: of({ tipoServicio: new TipoServicio(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SaturnoTestModule],
                declarations: [TipoServicioDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TipoServicioDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TipoServicioDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.tipoServicio).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
