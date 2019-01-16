import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHierarchy } from 'app/shared/model/hierarchy.model';

@Component({
    selector: 'jhi-hierarchy-detail',
    templateUrl: './hierarchy-detail.component.html'
})
export class HierarchyDetailComponent implements OnInit {
    hierarchy: IHierarchy;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ hierarchy }) => {
            this.hierarchy = hierarchy;
        });
    }

    previousState() {
        window.history.back();
    }
}
