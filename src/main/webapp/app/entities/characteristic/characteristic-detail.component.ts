import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICharacteristic } from 'app/shared/model/characteristic.model';

@Component({
    selector: 'jhi-characteristic-detail',
    templateUrl: './characteristic-detail.component.html'
})
export class CharacteristicDetailComponent implements OnInit {
    characteristic: ICharacteristic;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ characteristic }) => {
            this.characteristic = characteristic;
        });
    }

    previousState() {
        window.history.back();
    }
}
