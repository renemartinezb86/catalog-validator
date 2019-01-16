import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CatalogValidatorSharedModule } from 'app/shared';
import {
    TaxModelComponent,
    TaxModelDetailComponent,
    TaxModelUpdateComponent,
    TaxModelDeletePopupComponent,
    TaxModelDeleteDialogComponent,
    taxModelRoute,
    taxModelPopupRoute
} from './';

const ENTITY_STATES = [...taxModelRoute, ...taxModelPopupRoute];

@NgModule({
    imports: [CatalogValidatorSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TaxModelComponent,
        TaxModelDetailComponent,
        TaxModelUpdateComponent,
        TaxModelDeleteDialogComponent,
        TaxModelDeletePopupComponent
    ],
    entryComponents: [TaxModelComponent, TaxModelUpdateComponent, TaxModelDeleteDialogComponent, TaxModelDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CatalogValidatorTaxModelModule {}
