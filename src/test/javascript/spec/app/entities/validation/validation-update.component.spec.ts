/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CatalogValidatorTestModule } from '../../../test.module';
import { ValidationUpdateComponent } from 'app/entities/validation/validation-update.component';
import { ValidationService } from 'app/entities/validation/validation.service';
import { Validation } from 'app/shared/model/validation.model';

describe('Component Tests', () => {
    describe('Validation Management Update Component', () => {
        let comp: ValidationUpdateComponent;
        let fixture: ComponentFixture<ValidationUpdateComponent>;
        let service: ValidationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CatalogValidatorTestModule],
                declarations: [ValidationUpdateComponent]
            })
                .overrideTemplate(ValidationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ValidationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ValidationService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Validation('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.validation = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Validation();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.validation = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
