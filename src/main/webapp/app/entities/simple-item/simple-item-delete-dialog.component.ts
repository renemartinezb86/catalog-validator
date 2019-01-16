import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISimpleItem } from 'app/shared/model/simple-item.model';
import { SimpleItemService } from './simple-item.service';

@Component({
    selector: 'jhi-simple-item-delete-dialog',
    templateUrl: './simple-item-delete-dialog.component.html'
})
export class SimpleItemDeleteDialogComponent {
    simpleItem: ISimpleItem;

    constructor(
        protected simpleItemService: SimpleItemService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.simpleItemService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'simpleItemListModification',
                content: 'Deleted an simpleItem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-simple-item-delete-popup',
    template: ''
})
export class SimpleItemDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ simpleItem }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SimpleItemDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.simpleItem = simpleItem;
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
