/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CatalogValidatorTestModule } from '../../../test.module';
import { HierarchyUpdateComponent } from 'app/entities/hierarchy/hierarchy-update.component';
import { HierarchyService } from 'app/entities/hierarchy/hierarchy.service';
import { Hierarchy } from 'app/shared/model/hierarchy.model';

describe('Component Tests', () => {
    describe('Hierarchy Management Update Component', () => {
        let comp: HierarchyUpdateComponent;
        let fixture: ComponentFixture<HierarchyUpdateComponent>;
        let service: HierarchyService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CatalogValidatorTestModule],
                declarations: [HierarchyUpdateComponent]
            })
                .overrideTemplate(HierarchyUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HierarchyUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HierarchyService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Hierarchy('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.hierarchy = entity;
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
                    const entity = new Hierarchy();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.hierarchy = entity;
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
