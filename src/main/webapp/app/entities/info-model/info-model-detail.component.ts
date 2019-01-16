import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInfoModel } from 'app/shared/model/info-model.model';

@Component({
    selector: 'jhi-info-model-detail',
    templateUrl: './info-model-detail.component.html'
})
export class InfoModelDetailComponent implements OnInit {
    infoModel: IInfoModel;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ infoModel }) => {
            this.infoModel = infoModel;
        });
    }

    previousState() {
        window.history.back();
    }
}
