/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CatalogValidatorTestModule } from '../../../test.module';
import { CharacteristicUpdateComponent } from 'app/entities/characteristic/characteristic-update.component';
import { CharacteristicService } from 'app/entities/characteristic/characteristic.service';
import { Characteristic } from 'app/shared/model/characteristic.model';

describe('Component Tests', () => {
    describe('Characteristic Management Update Component', () => {
        let comp: CharacteristicUpdateComponent;
        let fixture: ComponentFixture<CharacteristicUpdateComponent>;
        let service: CharacteristicService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CatalogValidatorTestModule],
                declarations: [CharacteristicUpdateComponent]
            })
                .overrideTemplate(CharacteristicUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CharacteristicUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CharacteristicService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Characteristic('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.characteristic = entity;
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
                    const entity = new Characteristic();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.characteristic = entity;
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
