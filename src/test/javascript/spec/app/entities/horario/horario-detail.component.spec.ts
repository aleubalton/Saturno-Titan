/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaturnoTestModule } from '../../../test.module';
import { HorarioDetailComponent } from 'app/entities/horario/horario-detail.component';
import { Horario } from 'app/shared/model/horario.model';

describe('Component Tests', () => {
    describe('Horario Management Detail Component', () => {
        let comp: HorarioDetailComponent;
        let fixture: ComponentFixture<HorarioDetailComponent>;
        const route = ({ data: of({ horario: new Horario(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SaturnoTestModule],
                declarations: [HorarioDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(HorarioDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HorarioDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.horario).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
