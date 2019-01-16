/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CatalogValidatorTestModule } from '../../../test.module';
import { TaxModelUpdateComponent } from 'app/entities/tax-model/tax-model-update.component';
import { TaxModelService } from 'app/entities/tax-model/tax-model.service';
import { TaxModel } from 'app/shared/model/tax-model.model';

describe('Component Tests', () => {
    describe('TaxModel Management Update Component', () => {
        let comp: TaxModelUpdateComponent;
        let fixture: ComponentFixture<TaxModelUpdateComponent>;
        let service: TaxModelService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CatalogValidatorTestModule],
                declarations: [TaxModelUpdateComponent]
            })
                .overrideTemplate(TaxModelUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TaxModelUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TaxModelService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TaxModel('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.taxModel = entity;
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
                    const entity = new TaxModel();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.taxModel = entity;
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
