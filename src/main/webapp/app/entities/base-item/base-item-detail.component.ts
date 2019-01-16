import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBaseItem } from 'app/shared/model/base-item.model';

@Component({
    selector: 'jhi-base-item-detail',
    templateUrl: './base-item-detail.component.html'
})
export class BaseItemDetailComponent implements OnInit {
    baseItem: IBaseItem;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ baseItem }) => {
            this.baseItem = baseItem;
        });
    }

    previousState() {
        window.history.back();
    }
}
