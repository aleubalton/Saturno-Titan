/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaturnoTestModule } from '../../../test.module';
import { HorarioEspecialDetailComponent } from 'app/entities/horario-especial/horario-especial-detail.component';
import { HorarioEspecial } from 'app/shared/model/horario-especial.model';

describe('Component Tests', () => {
    describe('HorarioEspecial Management Detail Component', () => {
        let comp: HorarioEspecialDetailComponent;
        let fixture: ComponentFixture<HorarioEspecialDetailComponent>;
        const route = ({ data: of({ horarioEspecial: new HorarioEspecial(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SaturnoTestModule],
                declarations: [HorarioEspecialDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(HorarioEspecialDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HorarioEspecialDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.horarioEspecial).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
