import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IInfoModel } from 'app/shared/model/info-model.model';
import { InfoModelService } from './info-model.service';
import { ICatalog } from 'app/shared/model/catalog.model';
import { CatalogService } from 'app/entities/catalog';

@Component({
    selector: 'jhi-info-model-update',
    templateUrl: './info-model-update.component.html'
})
export class InfoModelUpdateComponent implements OnInit {
    infoModel: IInfoModel;
    isSaving: boolean;

    catalogs: ICatalog[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected infoModelService: InfoModelService,
        protected catalogService: CatalogService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ infoModel }) => {
            this.infoModel = infoModel;
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
        if (this.infoModel.id !== undefined) {
            this.subscribeToSaveResponse(this.infoModelService.update(this.infoModel));
        } else {
            this.subscribeToSaveResponse(this.infoModelService.create(this.infoModel));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IInfoModel>>) {
        result.subscribe((res: HttpResponse<IInfoModel>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
