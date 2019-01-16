import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CatalogValidatorSharedModule } from 'app/shared';
import {
    InfoModelAttrComponent,
    InfoModelAttrDetailComponent,
    InfoModelAttrUpdateComponent,
    InfoModelAttrDeletePopupComponent,
    InfoModelAttrDeleteDialogComponent,
    infoModelAttrRoute,
    infoModelAttrPopupRoute
} from './';

const ENTITY_STATES = [...infoModelAttrRoute, ...infoModelAttrPopupRoute];

@NgModule({
    imports: [CatalogValidatorSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        InfoModelAttrComponent,
        InfoModelAttrDetailComponent,
        InfoModelAttrUpdateComponent,
        InfoModelAttrDeleteDialogComponent,
        InfoModelAttrDeletePopupComponent
    ],
    entryComponents: [
        InfoModelAttrComponent,
        InfoModelAttrUpdateComponent,
        InfoModelAttrDeleteDialogComponent,
        InfoModelAttrDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CatalogValidatorInfoModelAttrModule {}
