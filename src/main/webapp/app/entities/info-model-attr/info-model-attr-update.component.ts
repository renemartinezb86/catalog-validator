import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IInfoModelAttr } from 'app/shared/model/info-model-attr.model';
import { InfoModelAttrService } from './info-model-attr.service';
import { IInfoModel } from 'app/shared/model/info-model.model';
import { InfoModelService } from 'app/entities/info-model';

@Component({
    selector: 'jhi-info-model-attr-update',
    templateUrl: './info-model-attr-update.component.html'
})
export class InfoModelAttrUpdateComponent implements OnInit {
    infoModelAttr: IInfoModelAttr;
    isSaving: boolean;

    infomodels: IInfoModel[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected infoModelAttrService: InfoModelAttrService,
        protected infoModelService: InfoModelService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ infoModelAttr }) => {
            this.infoModelAttr = infoModelAttr;
        });
        this.infoModelService.query().subscribe(
            (res: HttpResponse<IInfoModel[]>) => {
                this.infomodels = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.infoModelAttr.id !== undefined) {
            this.subscribeToSaveResponse(this.infoModelAttrService.update(this.infoModelAttr));
        } else {
            this.subscribeToSaveResponse(this.infoModelAttrService.create(this.infoModelAttr));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IInfoModelAttr>>) {
        result.subscribe((res: HttpResponse<IInfoModelAttr>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackInfoModelById(index: number, item: IInfoModel) {
        return item.id;
    }
}
