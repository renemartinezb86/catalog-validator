/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CatalogValidatorTestModule } from '../../../test.module';
import { InfoModelUpdateComponent } from 'app/entities/info-model/info-model-update.component';
import { InfoModelService } from 'app/entities/info-model/info-model.service';
import { InfoModel } from 'app/shared/model/info-model.model';

describe('Component Tests', () => {
    describe('InfoModel Management Update Component', () => {
        let comp: InfoModelUpdateComponent;
        let fixture: ComponentFixture<InfoModelUpdateComponent>;
        let service: InfoModelService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CatalogValidatorTestModule],
                declarations: [InfoModelUpdateComponent]
            })
                .overrideTemplate(InfoModelUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(InfoModelUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InfoModelService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new InfoModel('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.infoModel = entity;
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
                    const entity = new InfoModel();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.infoModel = entity;
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
