import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHierarchy } from 'app/shared/model/hierarchy.model';
import { HierarchyService } from './hierarchy.service';

@Component({
    selector: 'jhi-hierarchy-delete-dialog',
    templateUrl: './hierarchy-delete-dialog.component.html'
})
export class HierarchyDeleteDialogComponent {
    hierarchy: IHierarchy;

    constructor(
        protected hierarchyService: HierarchyService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.hierarchyService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'hierarchyListModification',
                content: 'Deleted an hierarchy'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-hierarchy-delete-popup',
    template: ''
})
export class HierarchyDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ hierarchy }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(HierarchyDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.hierarchy = hierarchy;
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
