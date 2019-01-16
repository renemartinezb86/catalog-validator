import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITaxModel } from 'app/shared/model/tax-model.model';
import { TaxModelService } from './tax-model.service';

@Component({
    selector: 'jhi-tax-model-delete-dialog',
    templateUrl: './tax-model-delete-dialog.component.html'
})
export class TaxModelDeleteDialogComponent {
    taxModel: ITaxModel;

    constructor(protected taxModelService: TaxModelService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.taxModelService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'taxModelListModification',
                content: 'Deleted an taxModel'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tax-model-delete-popup',
    template: ''
})
export class TaxModelDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ taxModel }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TaxModelDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.taxModel = taxModel;
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
