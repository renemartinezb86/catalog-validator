import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBaseItem } from 'app/shared/model/base-item.model';
import { BaseItemService } from './base-item.service';

@Component({
    selector: 'jhi-base-item-delete-dialog',
    templateUrl: './base-item-delete-dialog.component.html'
})
export class BaseItemDeleteDialogComponent {
    baseItem: IBaseItem;

    constructor(protected baseItemService: BaseItemService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.baseItemService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'baseItemListModification',
                content: 'Deleted an baseItem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-base-item-delete-popup',
    template: ''
})
export class BaseItemDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ baseItem }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BaseItemDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.baseItem = baseItem;
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
