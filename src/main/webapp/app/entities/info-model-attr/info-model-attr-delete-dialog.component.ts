import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInfoModelAttr } from 'app/shared/model/info-model-attr.model';
import { InfoModelAttrService } from './info-model-attr.service';

@Component({
    selector: 'jhi-info-model-attr-delete-dialog',
    templateUrl: './info-model-attr-delete-dialog.component.html'
})
export class InfoModelAttrDeleteDialogComponent {
    infoModelAttr: IInfoModelAttr;

    constructor(
        protected infoModelAttrService: InfoModelAttrService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.infoModelAttrService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'infoModelAttrListModification',
                content: 'Deleted an infoModelAttr'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-info-model-attr-delete-popup',
    template: ''
})
export class InfoModelAttrDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ infoModelAttr }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(InfoModelAttrDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.infoModelAttr = infoModelAttr;
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
