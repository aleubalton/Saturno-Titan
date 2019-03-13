/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaturnoTestModule } from '../../../test.module';
import { DiaNoLaborableDetailComponent } from 'app/entities/dia-no-laborable/dia-no-laborable-detail.component';
import { DiaNoLaborable } from 'app/shared/model/dia-no-laborable.model';

describe('Component Tests', () => {
    describe('DiaNoLaborable Management Detail Component', () => {
        let comp: DiaNoLaborableDetailComponent;
        let fixture: ComponentFixture<DiaNoLaborableDetailComponent>;
        const route = ({ data: of({ diaNoLaborable: new DiaNoLaborable(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SaturnoTestModule],
                declarations: [DiaNoLaborableDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DiaNoLaborableDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DiaNoLaborableDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.diaNoLaborable).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
