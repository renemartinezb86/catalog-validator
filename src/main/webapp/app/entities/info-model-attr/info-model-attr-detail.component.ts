import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInfoModelAttr } from 'app/shared/model/info-model-attr.model';

@Component({
    selector: 'jhi-info-model-attr-detail',
    templateUrl: './info-model-attr-detail.component.html'
})
export class InfoModelAttrDetailComponent implements OnInit {
    infoModelAttr: IInfoModelAttr;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ infoModelAttr }) => {
            this.infoModelAttr = infoModelAttr;
        });
    }

    previousState() {
        window.history.back();
    }
}
