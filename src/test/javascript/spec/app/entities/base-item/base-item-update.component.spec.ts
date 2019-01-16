/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CatalogValidatorTestModule } from '../../../test.module';
import { BaseItemUpdateComponent } from 'app/entities/base-item/base-item-update.component';
import { BaseItemService } from 'app/entities/base-item/base-item.service';
import { BaseItem } from 'app/shared/model/base-item.model';

describe('Component Tests', () => {
    describe('BaseItem Management Update Component', () => {
        let comp: BaseItemUpdateComponent;
        let fixture: ComponentFixture<BaseItemUpdateComponent>;
        let service: BaseItemService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CatalogValidatorTestModule],
                declarations: [BaseItemUpdateComponent]
            })
                .overrideTemplate(BaseItemUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BaseItemUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BaseItemService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new BaseItem('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.baseItem = entity;
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
                    const entity = new BaseItem();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.baseItem = entity;
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
