/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CatalogValidatorTestModule } from '../../../test.module';
import { BaseItemDeleteDialogComponent } from 'app/entities/base-item/base-item-delete-dialog.component';
import { BaseItemService } from 'app/entities/base-item/base-item.service';

describe('Component Tests', () => {
    describe('BaseItem Management Delete Component', () => {
        let comp: BaseItemDeleteDialogComponent;
        let fixture: ComponentFixture<BaseItemDeleteDialogComponent>;
        let service: BaseItemService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CatalogValidatorTestModule],
                declarations: [BaseItemDeleteDialogComponent]
            })
                .overrideTemplate(BaseItemDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BaseItemDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BaseItemService);
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
