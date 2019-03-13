/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaturnoTestModule } from '../../../test.module';
import { PrecioServicioDetailComponent } from 'app/entities/precio-servicio/precio-servicio-detail.component';
import { PrecioServicio } from 'app/shared/model/precio-servicio.model';

describe('Component Tests', () => {
    describe('PrecioServicio Management Detail Component', () => {
        let comp: PrecioServicioDetailComponent;
        let fixture: ComponentFixture<PrecioServicioDetailComponent>;
        const route = ({ data: of({ precioServicio: new PrecioServicio(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SaturnoTestModule],
                declarations: [PrecioServicioDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PrecioServicioDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PrecioServicioDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.precioServicio).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
