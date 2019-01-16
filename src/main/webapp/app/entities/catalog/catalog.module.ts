import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CatalogValidatorSharedModule } from 'app/shared';
import {
    CatalogComponent,
    CatalogDetailComponent,
    CatalogUpdateComponent,
    CatalogDeletePopupComponent,
    CatalogDeleteDialogComponent,
    catalogRoute,
    catalogPopupRoute
} from './';

const ENTITY_STATES = [...catalogRoute, ...catalogPopupRoute];

@NgModule({
    imports: [CatalogValidatorSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CatalogComponent,
        CatalogDetailComponent,
        CatalogUpdateComponent,
        CatalogDeleteDialogComponent,
        CatalogDeletePopupComponent
    ],
    entryComponents: [CatalogComponent, CatalogUpdateComponent, CatalogDeleteDialogComponent, CatalogDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CatalogValidatorCatalogModule {}
