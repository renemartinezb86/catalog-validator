/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CatalogValidatorTestModule } from '../../../test.module';
import { SimpleItemUpdateComponent } from 'app/entities/simple-item/simple-item-update.component';
import { SimpleItemService } from 'app/entities/simple-item/simple-item.service';
import { SimpleItem } from 'app/shared/model/simple-item.model';

describe('Component Tests', () => {
    describe('SimpleItem Management Update Component', () => {
        let comp: SimpleItemUpdateComponent;
        let fixture: ComponentFixture<SimpleItemUpdateComponent>;
        let service: SimpleItemService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CatalogValidatorTestModule],
                declarations: [SimpleItemUpdateComponent]
            })
                .overrideTemplate(SimpleItemUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SimpleItemUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SimpleItemService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SimpleItem('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.simpleItem = entity;
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
                    const entity = new SimpleItem();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.simpleItem = entity;
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
