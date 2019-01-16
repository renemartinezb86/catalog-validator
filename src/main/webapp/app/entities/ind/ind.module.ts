import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CatalogValidatorSharedModule } from 'app/shared';
import {
    INDComponent,
    INDDetailComponent,
    INDUpdateComponent,
    INDDeletePopupComponent,
    INDDeleteDialogComponent,
    iNDRoute,
    iNDPopupRoute
} from './';

const ENTITY_STATES = [...iNDRoute, ...iNDPopupRoute];

@NgModule({
    imports: [CatalogValidatorSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [INDComponent, INDDetailComponent, INDUpdateComponent, INDDeleteDialogComponent, INDDeletePopupComponent],
    entryComponents: [INDComponent, INDUpdateComponent, INDDeleteDialogComponent, INDDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CatalogValidatorINDModule {}
