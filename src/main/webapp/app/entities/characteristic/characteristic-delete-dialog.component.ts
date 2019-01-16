import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICharacteristic } from 'app/shared/model/characteristic.model';
import { CharacteristicService } from './characteristic.service';

@Component({
    selector: 'jhi-characteristic-delete-dialog',
    templateUrl: './characteristic-delete-dialog.component.html'
})
export class CharacteristicDeleteDialogComponent {
    characteristic: ICharacteristic;

    constructor(
        protected characteristicService: CharacteristicService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.characteristicService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'characteristicListModification',
                content: 'Deleted an characteristic'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-characteristic-delete-popup',
    template: ''
})
export class CharacteristicDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ characteristic }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CharacteristicDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.characteristic = characteristic;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
