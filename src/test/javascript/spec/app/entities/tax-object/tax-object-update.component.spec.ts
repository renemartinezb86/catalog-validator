/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CatalogValidatorTestModule } from '../../../test.module';
import { TaxObjectUpdateComponent } from 'app/entities/tax-object/tax-object-update.component';
import { TaxObjectService } from 'app/entities/tax-object/tax-object.service';
import { TaxObject } from 'app/shared/model/tax-object.model';

describe('Component Tests', () => {
    describe('TaxObject Management Update Component', () => {
        let comp: TaxObjectUpdateComponent;
        let fixture: ComponentFixture<TaxObjectUpdateComponent>;
        let service: TaxObjectService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CatalogValidatorTestModule],
                declarations: [TaxObjectUpdateComponent]
            })
                .overrideTemplate(TaxObjectUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TaxObjectUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TaxObjectService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TaxObject('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.taxObject = entity;
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
                    const entity = new TaxObject();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.taxObject = entity;
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
