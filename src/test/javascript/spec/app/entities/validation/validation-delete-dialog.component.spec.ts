/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CatalogValidatorTestModule } from '../../../test.module';
import { ValidationDeleteDialogComponent } from 'app/entities/validation/validation-delete-dialog.component';
import { ValidationService } from 'app/entities/validation/validation.service';

describe('Component Tests', () => {
    describe('Validation Management Delete Component', () => {
        let comp: ValidationDeleteDialogComponent;
        let fixture: ComponentFixture<ValidationDeleteDialogComponent>;
        let service: ValidationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CatalogValidatorTestModule],
                declarations: [ValidationDeleteDialogComponent]
            })
                .overrideTemplate(ValidationDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ValidationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ValidationService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete('123');
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith('123');
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
