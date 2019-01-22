/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaturnoTestModule } from '../../../test.module';
import { IntervaloDetailComponent } from 'app/entities/intervalo/intervalo-detail.component';
import { Intervalo } from 'app/shared/model/intervalo.model';

describe('Component Tests', () => {
    describe('Intervalo Management Detail Component', () => {
        let comp: IntervaloDetailComponent;
        let fixture: ComponentFixture<IntervaloDetailComponent>;
        const route = ({ data: of({ intervalo: new Intervalo(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SaturnoTestModule],
                declarations: [IntervaloDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(IntervaloDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IntervaloDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.intervalo).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
