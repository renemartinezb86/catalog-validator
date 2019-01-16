/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CatalogValidatorTestModule } from '../../../test.module';
import { INDUpdateComponent } from 'app/entities/ind/ind-update.component';
import { INDService } from 'app/entities/ind/ind.service';
import { IND } from 'app/shared/model/ind.model';

describe('Component Tests', () => {
    describe('IND Management Update Component', () => {
        let comp: INDUpdateComponent;
        let fixture: ComponentFixture<INDUpdateComponent>;
        let service: INDService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CatalogValidatorTestModule],
                declarations: [INDUpdateComponent]
            })
                .overrideTemplate(INDUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(INDUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(INDService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new IND('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.iND = entity;
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
                    const entity = new IND();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.iND = entity;
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
