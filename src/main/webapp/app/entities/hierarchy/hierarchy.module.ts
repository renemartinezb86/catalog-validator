import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CatalogValidatorSharedModule } from 'app/shared';
import {
    HierarchyComponent,
    HierarchyDetailComponent,
    HierarchyUpdateComponent,
    HierarchyDeletePopupComponent,
    HierarchyDeleteDialogComponent,
    hierarchyRoute,
    hierarchyPopupRoute
} from './';

const ENTITY_STATES = [...hierarchyRoute, ...hierarchyPopupRoute];

@NgModule({
    imports: [CatalogValidatorSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        HierarchyComponent,
        HierarchyDetailComponent,
        HierarchyUpdateComponent,
        HierarchyDeleteDialogComponent,
        HierarchyDeletePopupComponent
    ],
    entryComponents: [HierarchyComponent, HierarchyUpdateComponent, HierarchyDeleteDialogComponent, HierarchyDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CatalogValidatorHierarchyModule {}
