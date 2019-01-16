import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CatalogValidatorSharedModule } from 'app/shared';
import {
    TaxObjectComponent,
    TaxObjectDetailComponent,
    TaxObjectUpdateComponent,
    TaxObjectDeletePopupComponent,
    TaxObjectDeleteDialogComponent,
    taxObjectRoute,
    taxObjectPopupRoute
} from './';

const ENTITY_STATES = [...taxObjectRoute, ...taxObjectPopupRoute];

@NgModule({
    imports: [CatalogValidatorSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TaxObjectComponent,
        TaxObjectDetailComponent,
        TaxObjectUpdateComponent,
        TaxObjectDeleteDialogComponent,
        TaxObjectDeletePopupComponent
    ],
    entryComponents: [TaxObjectComponent, TaxObjectUpdateComponent, TaxObjectDeleteDialogComponent, TaxObjectDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CatalogValidatorTaxObjectModule {}
