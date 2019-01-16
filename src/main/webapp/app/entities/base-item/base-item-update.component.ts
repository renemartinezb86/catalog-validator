import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IBaseItem } from 'app/shared/model/base-item.model';
import { BaseItemService } from './base-item.service';
import { ICatalog } from 'app/shared/model/catalog.model';
import { CatalogService } from 'app/entities/catalog';

@Component({
    selector: 'jhi-base-item-update',
    templateUrl: './base-item-update.component.html'
})
export class BaseItemUpdateComponent implements OnInit {
    baseItem: IBaseItem;
    isSaving: boolean;

    catalogs: ICatalog[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected baseItemService: BaseItemService,
        protected catalogService: CatalogService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ baseItem }) => {
            this.baseItem = baseItem;
        });
        this.catalogService.query().subscribe(
            (res: HttpResponse<ICatalog[]>) => {
                this.catalogs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.baseItem.id !== undefined) {
            this.subscribeToSaveResponse(this.baseItemService.update(this.baseItem));
        } else {
            this.subscribeToSaveResponse(this.baseItemService.create(this.baseItem));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IBaseItem>>) {
        result.subscribe((res: HttpResponse<IBaseItem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
