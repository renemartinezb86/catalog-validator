import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISimpleItem } from 'app/shared/model/simple-item.model';

@Component({
    selector: 'jhi-simple-item-detail',
    templateUrl: './simple-item-detail.component.html'
})
export class SimpleItemDetailComponent implements OnInit {
    simpleItem: ISimpleItem;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ simpleItem }) => {
            this.simpleItem = simpleItem;
        });
    }

    previousState() {
        window.history.back();
    }
}
