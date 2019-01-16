import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ISimpleItem } from 'app/shared/model/simple-item.model';
import { SimpleItemService } from './simple-item.service';

@Component({
    selector: 'jhi-simple-item-update',
    templateUrl: './simple-item-update.component.html'
})
export class SimpleItemUpdateComponent implements OnInit {
    simpleItem: ISimpleItem;
    isSaving: boolean;

    constructor(protected simpleItemService: SimpleItemService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ simpleItem }) => {
            this.simpleItem = simpleItem;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.simpleItem.id !== undefined) {
            this.subscribeToSaveResponse(this.simpleItemService.update(this.simpleItem));
        } else {
            this.subscribeToSaveResponse(this.simpleItemService.create(this.simpleItem));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISimpleItem>>) {
        result.subscribe((res: HttpResponse<ISimpleItem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
