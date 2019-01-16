import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CatalogValidatorSharedModule } from 'app/shared';
import {
    ValidationComponent,
    ValidationDetailComponent,
    ValidationUpdateComponent,
    ValidationDeletePopupComponent,
    ValidationDeleteDialogComponent,
    validationRoute,
    validationPopupRoute
} from './';

const ENTITY_STATES = [...validationRoute, ...validationPopupRoute];

@NgModule({
    imports: [CatalogValidatorSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ValidationComponent,
        ValidationDetailComponent,
        ValidationUpdateComponent,
        ValidationDeleteDialogComponent,
        ValidationDeletePopupComponent
    ],
    entryComponents: [ValidationComponent, ValidationUpdateComponent, ValidationDeleteDialogComponent, ValidationDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CatalogValidatorValidationModule {}
