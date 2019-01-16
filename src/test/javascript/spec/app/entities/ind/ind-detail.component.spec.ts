/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CatalogValidatorTestModule } from '../../../test.module';
import { INDDetailComponent } from 'app/entities/ind/ind-detail.component';
import { IND } from 'app/shared/model/ind.model';

describe('Component Tests', () => {
    describe('IND Management Detail Component', () => {
        let comp: INDDetailComponent;
        let fixture: ComponentFixture<INDDetailComponent>;
        const route = ({ data: of({ iND: new IND('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CatalogValidatorTestModule],
                declarations: [INDDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(INDDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(INDDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.iND).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
