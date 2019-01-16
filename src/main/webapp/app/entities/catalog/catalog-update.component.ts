import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICatalog } from 'app/shared/model/catalog.model';
import { CatalogService } from './catalog.service';

@Component({
    selector: 'jhi-catalog-update',
    templateUrl: './catalog-update.component.html'
})
export class CatalogUpdateComponent implements OnInit {
    catalog: ICatalog;
    isSaving: boolean;
    createdDate: string;

    constructor(protected catalogService: CatalogService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ catalog }) => {
            this.catalog = catalog;
            this.createdDate = this.catalog.createdDate != null ? this.catalog.createdDate.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.catalog.createdDate = this.createdDate != null ? moment(this.createdDate, DATE_TIME_FORMAT) : null;
        if (this.catalog.id !== undefined) {
            this.subscribeToSaveResponse(this.catalogService.update(this.catalog));
        } else {
            this.subscribeToSaveResponse(this.catalogService.create(this.catalog));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICatalog>>) {
        result.subscribe((res: HttpResponse<ICatalog>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
