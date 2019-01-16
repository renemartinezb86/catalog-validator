import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaxModel } from 'app/shared/model/tax-model.model';

@Component({
    selector: 'jhi-tax-model-detail',
    templateUrl: './tax-model-detail.component.html'
})
export class TaxModelDetailComponent implements OnInit {
    taxModel: ITaxModel;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ taxModel }) => {
            this.taxModel = taxModel;
        });
    }

    previousState() {
        window.history.back();
    }
}
