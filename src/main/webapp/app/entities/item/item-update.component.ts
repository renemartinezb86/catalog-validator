import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IItem } from 'app/shared/model/item.model';
import { ItemService } from './item.service';
import { ISimpleItem } from 'app/shared/model/simple-item.model';
import { SimpleItemService } from 'app/entities/simple-item';
import { IIND } from 'app/shared/model/ind.model';
import { INDService } from 'app/entities/ind';

@Component({
    selector: 'jhi-item-update',
    templateUrl: './item-update.component.html'
})
export class ItemUpdateComponent implements OnInit {
    item: IItem;
    isSaving: boolean;

    simpleitems: ISimpleItem[];

    inds: IIND[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected itemService: ItemService,
        protected simpleItemService: SimpleItemService,
        protected iNDService: INDService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ item }) => {
            this.item = item;
        });
        this.simpleItemService.query({ filter: 'item-is-null' }).subscribe(
            (res: HttpResponse<ISimpleItem[]>) => {
                if (!this.item.simpleItem || !this.item.simpleItem.id) {
                    this.simpleitems = res.body;
                } else {
                    this.simpleItemService.find(this.item.simpleItem.id).subscribe(
                        (subRes: HttpResponse<ISimpleItem>) => {
                            this.simpleitems = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.iNDService.query({ filter: 'item-is-null' }).subscribe(
            (res: HttpResponse<IIND[]>) => {
                if (!this.item.ind || !this.item.ind.id) {
                    this.inds = res.body;
                } else {
                    this.iNDService.find(this.item.ind.id).subscribe(
                        (subRes: HttpResponse<IIND>) => {
                            this.inds = [subRes.body].concat(res.body);
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
        if (this.item.id !== undefined) {
            this.subscribeToSaveResponse(this.itemService.update(this.item));
        } else {
            this.subscribeToSaveResponse(this.itemService.create(this.item));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IItem>>) {
        result.subscribe((res: HttpResponse<IItem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSimpleItemById(index: number, item: ISimpleItem) {
        return item.id;
    }

    trackINDById(index: number, item: IIND) {
        return item.id;
    }
}
