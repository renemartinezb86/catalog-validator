/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CatalogValidatorTestModule } from '../../../test.module';
import { TaxObjectDetailComponent } from 'app/entities/tax-object/tax-object-detail.component';
import { TaxObject } from 'app/shared/model/tax-object.model';

describe('Component Tests', () => {
    describe('TaxObject Management Detail Component', () => {
        let comp: TaxObjectDetailComponent;
        let fixture: ComponentFixture<TaxObjectDetailComponent>;
        const route = ({ data: of({ taxObject: new TaxObject('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CatalogValidatorTestModule],
                declarations: [TaxObjectDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TaxObjectDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TaxObjectDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.taxObject).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
