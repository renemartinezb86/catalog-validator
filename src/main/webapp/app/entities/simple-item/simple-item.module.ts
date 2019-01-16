import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CatalogValidatorSharedModule } from 'app/shared';
import {
    SimpleItemComponent,
    SimpleItemDetailComponent,
    SimpleItemUpdateComponent,
    SimpleItemDeletePopupComponent,
    SimpleItemDeleteDialogComponent,
    simpleItemRoute,
    simpleItemPopupRoute
} from './';

const ENTITY_STATES = [...simpleItemRoute, ...simpleItemPopupRoute];

@NgModule({
    imports: [CatalogValidatorSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SimpleItemComponent,
        SimpleItemDetailComponent,
        SimpleItemUpdateComponent,
        SimpleItemDeleteDialogComponent,
        SimpleItemDeletePopupComponent
    ],
    entryComponents: [SimpleItemComponent, SimpleItemUpdateComponent, SimpleItemDeleteDialogComponent, SimpleItemDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CatalogValidatorSimpleItemModule {}
