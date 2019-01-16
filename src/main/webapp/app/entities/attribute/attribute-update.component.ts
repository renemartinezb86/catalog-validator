import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAttribute } from 'app/shared/model/attribute.model';
import { AttributeService } from './attribute.service';
import { IItem } from 'app/shared/model/item.model';
import { ItemService } from 'app/entities/item';

@Component({
    selector: 'jhi-attribute-update',
    templateUrl: './attribute-update.component.html'
})
export class AttributeUpdateComponent implements OnInit {
    attribute: IAttribute;
    isSaving: boolean;

    items: IItem[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected attributeService: AttributeService,
        protected itemService: ItemService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ attribute }) => {
            this.attribute = attribute;
        });
        this.itemService.query().subscribe(
            (res: HttpResponse<IItem[]>) => {
                this.items = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.attribute.id !== undefined) {
            this.subscribeToSaveResponse(this.attributeService.update(this.attribute));
        } else {
            this.subscribeToSaveResponse(this.attributeService.create(this.attribute));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAttribute>>) {
        result.subscribe((res: HttpResponse<IAttribute>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackItemById(index: number, item: IItem) {
        return item.id;
    }
}
