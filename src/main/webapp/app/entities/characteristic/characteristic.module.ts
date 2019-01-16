import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CatalogValidatorSharedModule } from 'app/shared';
import {
    CharacteristicComponent,
    CharacteristicDetailComponent,
    CharacteristicUpdateComponent,
    CharacteristicDeletePopupComponent,
    CharacteristicDeleteDialogComponent,
    characteristicRoute,
    characteristicPopupRoute
} from './';

const ENTITY_STATES = [...characteristicRoute, ...characteristicPopupRoute];

@NgModule({
    imports: [CatalogValidatorSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CharacteristicComponent,
        CharacteristicDetailComponent,
        CharacteristicUpdateComponent,
        CharacteristicDeleteDialogComponent,
        CharacteristicDeletePopupComponent
    ],
    entryComponents: [
        CharacteristicComponent,
        CharacteristicUpdateComponent,
        CharacteristicDeleteDialogComponent,
        CharacteristicDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CatalogValidatorCharacteristicModule {}
