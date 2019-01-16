/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CatalogValidatorTestModule } from '../../../test.module';
import { HierarchyDetailComponent } from 'app/entities/hierarchy/hierarchy-detail.component';
import { Hierarchy } from 'app/shared/model/hierarchy.model';

describe('Component Tests', () => {
    describe('Hierarchy Management Detail Component', () => {
        let comp: HierarchyDetailComponent;
        let fixture: ComponentFixture<HierarchyDetailComponent>;
        const route = ({ data: of({ hierarchy: new Hierarchy('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CatalogValidatorTestModule],
                declarations: [HierarchyDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(HierarchyDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HierarchyDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.hierarchy).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
