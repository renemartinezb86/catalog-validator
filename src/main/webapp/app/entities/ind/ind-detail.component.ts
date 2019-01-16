import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIND } from 'app/shared/model/ind.model';

@Component({
    selector: 'jhi-ind-detail',
    templateUrl: './ind-detail.component.html'
})
export class INDDetailComponent implements OnInit {
    iND: IIND;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ iND }) => {
            this.iND = iND;
        });
    }

    previousState() {
        window.history.back();
    }
}
