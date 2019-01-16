import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CatalogValidatorSharedModule } from 'app/shared';
import {
    InfoModelComponent,
    InfoModelDetailComponent,
    InfoModelUpdateComponent,
    InfoModelDeletePopupComponent,
    InfoModelDeleteDialogComponent,
    infoModelRoute,
    infoModelPopupRoute
} from './';

const ENTITY_STATES = [...infoModelRoute, ...infoModelPopupRoute];

@NgModule({
    imports: [CatalogValidatorSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        InfoModelComponent,
        InfoModelDetailComponent,
        InfoModelUpdateComponent,
        InfoModelDeleteDialogComponent,
        InfoModelDeletePopupComponent
    ],
    entryComponents: [InfoModelComponent, InfoModelUpdateComponent, InfoModelDeleteDialogComponent, InfoModelDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CatalogValidatorInfoModelModule {}
