import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaxObject } from 'app/shared/model/tax-object.model';

@Component({
    selector: 'jhi-tax-object-detail',
    templateUrl: './tax-object-detail.component.html'
})
export class TaxObjectDetailComponent implements OnInit {
    taxObject: ITaxObject;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ taxObject }) => {
            this.taxObject = taxObject;
        });
    }

    previousState() {
        window.history.back();
    }
}
