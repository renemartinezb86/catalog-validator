import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CatalogValidatorEnvironmentModule } from './environment/environment.module';
import { CatalogValidatorValidationModule } from './validation/validation.module';
import { CatalogValidatorCatalogModule } from './catalog/catalog.module';
import { CatalogValidatorINDModule } from './ind/ind.module';
import { CatalogValidatorTaxObjectModule } from './tax-object/tax-object.module';
import { CatalogValidatorTaxModelModule } from './tax-model/tax-model.module';
import { CatalogValidatorBaseItemModule } from './base-item/base-item.module';
import { CatalogValidatorHierarchyModule } from './hierarchy/hierarchy.module';
import { CatalogValidatorInfoModelModule } from './info-model/info-model.module';
import { CatalogValidatorInfoModelAttrModule } from './info-model-attr/info-model-attr.module';
import { CatalogValidatorItemModule } from './item/item.module';
import { CatalogValidatorSimpleItemModule } from './simple-item/simple-item.module';
import { CatalogValidatorCharacteristicModule } from './characteristic/characteristic.module';
import { CatalogValidatorAttributeModule } from './attribute/attribute.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        CatalogValidatorEnvironmentModule,
        CatalogValidatorValidationModule,
        CatalogValidatorCatalogModule,
        CatalogValidatorINDModule,
        CatalogValidatorTaxObjectModule,
        CatalogValidatorTaxModelModule,
        CatalogValidatorBaseItemModule,
        CatalogValidatorHierarchyModule,
        CatalogValidatorInfoModelModule,
        CatalogValidatorInfoModelAttrModule,
        CatalogValidatorItemModule,
        CatalogValidatorSimpleItemModule,
        CatalogValidatorCharacteristicModule,
        CatalogValidatorAttributeModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CatalogValidatorEntityModule {}
