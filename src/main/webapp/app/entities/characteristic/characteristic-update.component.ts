import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICharacteristic } from 'app/shared/model/characteristic.model';
import { CharacteristicService } from './characteristic.service';
import { IItem } from 'app/shared/model/item.model';
import { ItemService } from 'app/entities/item';

@Component({
    selector: 'jhi-characteristic-update',
    templateUrl: './characteristic-update.component.html'
})
export class CharacteristicUpdateComponent implements OnInit {
    characteristic: ICharacteristic;
    isSaving: boolean;

    items: IItem[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected characteristicService: CharacteristicService,
        protected itemService: ItemService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ characteristic }) => {
            this.characteristic = characteristic;
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
        if (this.characteristic.id !== undefined) {
            this.subscribeToSaveResponse(this.characteristicService.update(this.characteristic));
        } else {
            this.subscribeToSaveResponse(this.characteristicService.create(this.characteristic));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICharacteristic>>) {
        result.subscribe((res: HttpResponse<ICharacteristic>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
