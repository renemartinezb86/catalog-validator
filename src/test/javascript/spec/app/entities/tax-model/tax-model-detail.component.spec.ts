/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CatalogValidatorTestModule } from '../../../test.module';
import { TaxModelDetailComponent } from 'app/entities/tax-model/tax-model-detail.component';
import { TaxModel } from 'app/shared/model/tax-model.model';

describe('Component Tests', () => {
    describe('TaxModel Management Detail Component', () => {
        let comp: TaxModelDetailComponent;
        let fixture: ComponentFixture<TaxModelDetailComponent>;
        const route = ({ data: of({ taxModel: new TaxModel('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CatalogValidatorTestModule],
                declarations: [TaxModelDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TaxModelDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TaxModelDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.taxModel).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
