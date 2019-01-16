/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CatalogValidatorTestModule } from '../../../test.module';
import { INDDeleteDialogComponent } from 'app/entities/ind/ind-delete-dialog.component';
import { INDService } from 'app/entities/ind/ind.service';

describe('Component Tests', () => {
    describe('IND Management Delete Component', () => {
        let comp: INDDeleteDialogComponent;
        let fixture: ComponentFixture<INDDeleteDialogComponent>;
        let service: INDService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CatalogValidatorTestModule],
                declarations: [INDDeleteDialogComponent]
            })
                .overrideTemplate(INDDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(INDDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(INDService);
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
