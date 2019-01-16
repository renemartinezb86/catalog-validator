import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IValidation } from 'app/shared/model/validation.model';
import { ValidationService } from './validation.service';
import { IEnvironment } from 'app/shared/model/environment.model';
import { EnvironmentService } from 'app/entities/environment';
import { ICatalog } from 'app/shared/model/catalog.model';
import { CatalogService } from 'app/entities/catalog';

@Component({
    selector: 'jhi-validation-update',
    templateUrl: './validation-update.component.html'
})
export class ValidationUpdateComponent implements OnInit {
    validation: IValidation;
    isSaving: boolean;

    environments: IEnvironment[];

    catalogs: ICatalog[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected validationService: ValidationService,
        protected environmentService: EnvironmentService,
        protected catalogService: CatalogService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ validation }) => {
            this.validation = validation;
        });
        this.environmentService.query({ filter: 'validation-is-null' }).subscribe(
            (res: HttpResponse<IEnvironment[]>) => {
                if (!this.validation.environment || !this.validation.environment.id) {
                    this.environments = res.body;
                } else {
                    this.environmentService.find(this.validation.environment.id).subscribe(
                        (subRes: HttpResponse<IEnvironment>) => {
                            this.environments = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.catalogService.query({ filter: 'validation-is-null' }).subscribe(
            (res: HttpResponse<ICatalog[]>) => {
                if (!this.validation.catalog || !this.validation.catalog.id) {
                    this.catalogs = res.body;
                } else {
                    this.catalogService.find(this.validation.catalog.id).subscribe(
                        (subRes: HttpResponse<ICatalog>) => {
                            this.catalogs = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.validation.id !== undefined) {
            this.subscribeToSaveResponse(this.validationService.update(this.validation));
        } else {
            this.subscribeToSaveResponse(this.validationService.create(this.validation));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IValidation>>) {
        result.subscribe((res: HttpResponse<IValidation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackEnvironmentById(index: number, item: IEnvironment) {
        return item.id;
    }

    trackCatalogById(index: number, item: ICatalog) {
        return item.id;
    }
}
