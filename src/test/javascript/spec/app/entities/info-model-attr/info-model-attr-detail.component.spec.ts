/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CatalogValidatorTestModule } from '../../../test.module';
import { InfoModelAttrDetailComponent } from 'app/entities/info-model-attr/info-model-attr-detail.component';
import { InfoModelAttr } from 'app/shared/model/info-model-attr.model';

describe('Component Tests', () => {
    describe('InfoModelAttr Management Detail Component', () => {
        let comp: InfoModelAttrDetailComponent;
        let fixture: ComponentFixture<InfoModelAttrDetailComponent>;
        const route = ({ data: of({ infoModelAttr: new InfoModelAttr('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CatalogValidatorTestModule],
                declarations: [InfoModelAttrDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(InfoModelAttrDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(InfoModelAttrDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.infoModelAttr).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
