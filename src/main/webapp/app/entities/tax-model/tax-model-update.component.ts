import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ITaxModel } from 'app/shared/model/tax-model.model';
import { TaxModelService } from './tax-model.service';
import { ICatalog } from 'app/shared/model/catalog.model';
import { CatalogService } from 'app/entities/catalog';
import { IIND } from 'app/shared/model/ind.model';
import { INDService } from 'app/entities/ind';
import { ITaxObject } from 'app/shared/model/tax-object.model';
import { TaxObjectService } from 'app/entities/tax-object';

@Component({
    selector: 'jhi-tax-model-update',
    templateUrl: './tax-model-update.component.html'
})
export class TaxModelUpdateComponent implements OnInit {
    taxModel: ITaxModel;
    isSaving: boolean;

    catalogs: ICatalog[];

    oinds: IIND[];

    relatedtaxobjects: ITaxObject[];

    taxmodels: ITaxModel[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected taxModelService: TaxModelService,
        protected catalogService: CatalogService,
        protected iNDService: INDService,
        protected taxObjectService: TaxObjectService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ taxModel }) => {
            this.taxModel = taxModel;
        });
        this.catalogService.query().subscribe(
            (res: HttpResponse<ICatalog[]>) => {
                this.catalogs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.iNDService.query({ filter: 'taxmodel-is-null' }).subscribe(
            (res: HttpResponse<IIND[]>) => {
                if (!this.taxModel.oIND || !this.taxModel.oIND.id) {
                    this.oinds = res.body;
                } else {
                    this.iNDService.find(this.taxModel.oIND.id).subscribe(
                        (subRes: HttpResponse<IIND>) => {
                            this.oinds = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.taxObjectService.query({ filter: 'taxmodel-is-null' }).subscribe(
            (res: HttpResponse<ITaxObject[]>) => {
                if (!this.taxModel.relatedTaxObject || !this.taxModel.relatedTaxObject.id) {
                    this.relatedtaxobjects = res.body;
                } else {
                    this.taxObjectService.find(this.taxModel.relatedTaxObject.id).subscribe(
                        (subRes: HttpResponse<ITaxObject>) => {
                            this.relatedtaxobjects = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.taxModelService.query().subscribe(
            (res: HttpResponse<ITaxModel[]>) => {
                this.taxmodels = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.taxModel.id !== undefined) {
            this.subscribeToSaveResponse(this.taxModelService.update(this.taxModel));
        } else {
            this.subscribeToSaveResponse(this.taxModelService.create(this.taxModel));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaxModel>>) {
        result.subscribe((res: HttpResponse<ITaxModel>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackINDById(index: number, item: IIND) {
        return item.id;
    }

    trackTaxObjectById(index: number, item: ITaxObject) {
        return item.id;
    }

    trackTaxModelById(index: number, item: ITaxModel) {
        return item.id;
    }
}
