import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInfoModel } from 'app/shared/model/info-model.model';
import { InfoModelService } from './info-model.service';

@Component({
    selector: 'jhi-info-model-delete-dialog',
    templateUrl: './info-model-delete-dialog.component.html'
})
export class InfoModelDeleteDialogComponent {
    infoModel: IInfoModel;

    constructor(
        protected infoModelService: InfoModelService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.infoModelService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'infoModelListModification',
                content: 'Deleted an infoModel'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-info-model-delete-popup',
    template: ''
})
export class InfoModelDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ infoModel }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(InfoModelDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.infoModel = infoModel;
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
