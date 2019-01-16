import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITaxObject } from 'app/shared/model/tax-object.model';
import { TaxObjectService } from './tax-object.service';

@Component({
    selector: 'jhi-tax-object-update',
    templateUrl: './tax-object-update.component.html'
})
export class TaxObjectUpdateComponent implements OnInit {
    taxObject: ITaxObject;
    isSaving: boolean;

    constructor(protected taxObjectService: TaxObjectService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ taxObject }) => {
            this.taxObject = taxObject;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.taxObject.id !== undefined) {
            this.subscribeToSaveResponse(this.taxObjectService.update(this.taxObject));
        } else {
            this.subscribeToSaveResponse(this.taxObjectService.create(this.taxObject));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaxObject>>) {
        result.subscribe((res: HttpResponse<ITaxObject>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
