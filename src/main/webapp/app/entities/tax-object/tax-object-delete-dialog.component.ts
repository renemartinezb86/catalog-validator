import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITaxObject } from 'app/shared/model/tax-object.model';
import { TaxObjectService } from './tax-object.service';

@Component({
    selector: 'jhi-tax-object-delete-dialog',
    templateUrl: './tax-object-delete-dialog.component.html'
})
export class TaxObjectDeleteDialogComponent {
    taxObject: ITaxObject;

    constructor(
        protected taxObjectService: TaxObjectService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.taxObjectService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'taxObjectListModification',
                content: 'Deleted an taxObject'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tax-object-delete-popup',
    template: ''
})
export class TaxObjectDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ taxObject }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TaxObjectDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.taxObject = taxObject;
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
