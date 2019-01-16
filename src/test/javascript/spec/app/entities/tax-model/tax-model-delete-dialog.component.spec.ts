/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CatalogValidatorTestModule } from '../../../test.module';
import { TaxModelDeleteDialogComponent } from 'app/entities/tax-model/tax-model-delete-dialog.component';
import { TaxModelService } from 'app/entities/tax-model/tax-model.service';

describe('Component Tests', () => {
    describe('TaxModel Management Delete Component', () => {
        let comp: TaxModelDeleteDialogComponent;
        let fixture: ComponentFixture<TaxModelDeleteDialogComponent>;
        let service: TaxModelService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CatalogValidatorTestModule],
                declarations: [TaxModelDeleteDialogComponent]
            })
                .overrideTemplate(TaxModelDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TaxModelDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TaxModelService);
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
