import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IHierarchy } from 'app/shared/model/hierarchy.model';
import { HierarchyService } from './hierarchy.service';
import { ICatalog } from 'app/shared/model/catalog.model';
import { CatalogService } from 'app/entities/catalog';

@Component({
    selector: 'jhi-hierarchy-update',
    templateUrl: './hierarchy-update.component.html'
})
export class HierarchyUpdateComponent implements OnInit {
    hierarchy: IHierarchy;
    isSaving: boolean;

    catalogs: ICatalog[];

    hierarchies: IHierarchy[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected hierarchyService: HierarchyService,
        protected catalogService: CatalogService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ hierarchy }) => {
            this.hierarchy = hierarchy;
        });
        this.catalogService.query().subscribe(
            (res: HttpResponse<ICatalog[]>) => {
                this.catalogs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.hierarchyService.query().subscribe(
            (res: HttpResponse<IHierarchy[]>) => {
                this.hierarchies = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.hierarchy.id !== undefined) {
            this.subscribeToSaveResponse(this.hierarchyService.update(this.hierarchy));
        } else {
            this.subscribeToSaveResponse(this.hierarchyService.create(this.hierarchy));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IHierarchy>>) {
        result.subscribe((res: HttpResponse<IHierarchy>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCatalogById(index: number, item: ICatalog) {
        return item.id;
    }

    trackHierarchyById(index: number, item: IHierarchy) {
        return item.id;
    }
}
