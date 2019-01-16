/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CatalogValidatorTestModule } from '../../../test.module';
import { SimpleItemDetailComponent } from 'app/entities/simple-item/simple-item-detail.component';
import { SimpleItem } from 'app/shared/model/simple-item.model';

describe('Component Tests', () => {
    describe('SimpleItem Management Detail Component', () => {
        let comp: SimpleItemDetailComponent;
        let fixture: ComponentFixture<SimpleItemDetailComponent>;
        const route = ({ data: of({ simpleItem: new SimpleItem('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CatalogValidatorTestModule],
                declarations: [SimpleItemDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SimpleItemDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SimpleItemDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.simpleItem).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
