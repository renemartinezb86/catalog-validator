/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CatalogValidatorTestModule } from '../../../test.module';
import { ValidationDetailComponent } from 'app/entities/validation/validation-detail.component';
import { Validation } from 'app/shared/model/validation.model';

describe('Component Tests', () => {
    describe('Validation Management Detail Component', () => {
        let comp: ValidationDetailComponent;
        let fixture: ComponentFixture<ValidationDetailComponent>;
        const route = ({ data: of({ validation: new Validation('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CatalogValidatorTestModule],
                declarations: [ValidationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ValidationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ValidationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.validation).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
