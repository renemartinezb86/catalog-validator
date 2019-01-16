/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CatalogValidatorTestModule } from '../../../test.module';
import { BaseItemDetailComponent } from 'app/entities/base-item/base-item-detail.component';
import { BaseItem } from 'app/shared/model/base-item.model';

describe('Component Tests', () => {
    describe('BaseItem Management Detail Component', () => {
        let comp: BaseItemDetailComponent;
        let fixture: ComponentFixture<BaseItemDetailComponent>;
        const route = ({ data: of({ baseItem: new BaseItem('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CatalogValidatorTestModule],
                declarations: [BaseItemDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BaseItemDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BaseItemDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.baseItem).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
