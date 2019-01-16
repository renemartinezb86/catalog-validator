import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IIND } from 'app/shared/model/ind.model';
import { INDService } from './ind.service';

@Component({
    selector: 'jhi-ind-update',
    templateUrl: './ind-update.component.html'
})
export class INDUpdateComponent implements OnInit {
    iND: IIND;
    isSaving: boolean;

    constructor(protected iNDService: INDService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ iND }) => {
            this.iND = iND;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.iND.id !== undefined) {
            this.subscribeToSaveResponse(this.iNDService.update(this.iND));
        } else {
            this.subscribeToSaveResponse(this.iNDService.create(this.iND));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IIND>>) {
        result.subscribe((res: HttpResponse<IIND>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
