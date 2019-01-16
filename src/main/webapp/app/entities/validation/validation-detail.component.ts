import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IValidation } from 'app/shared/model/validation.model';

@Component({
    selector: 'jhi-validation-detail',
    templateUrl: './validation-detail.component.html'
})
export class ValidationDetailComponent implements OnInit {
    validation: IValidation;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ validation }) => {
            this.validation = validation;
        });
    }

    previousState() {
        window.history.back();
    }
}
