import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CatalogValidatorSharedModule } from 'app/shared';
import {
    BaseItemComponent,
    BaseItemDetailComponent,
    BaseItemUpdateComponent,
    BaseItemDeletePopupComponent,
    BaseItemDeleteDialogComponent,
    baseItemRoute,
    baseItemPopupRoute
} from './';

const ENTITY_STATES = [...baseItemRoute, ...baseItemPopupRoute];

@NgModule({
    imports: [CatalogValidatorSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BaseItemComponent,
        BaseItemDetailComponent,
        BaseItemUpdateComponent,
        BaseItemDeleteDialogComponent,
        BaseItemDeletePopupComponent
    ],
    entryComponents: [BaseItemComponent, BaseItemUpdateComponent, BaseItemDeleteDialogComponent, BaseItemDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CatalogValidatorBaseItemModule {}
