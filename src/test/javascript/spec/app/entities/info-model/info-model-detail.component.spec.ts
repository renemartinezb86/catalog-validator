/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CatalogValidatorTestModule } from '../../../test.module';
import { InfoModelDetailComponent } from 'app/entities/info-model/info-model-detail.component';
import { InfoModel } from 'app/shared/model/info-model.model';

describe('Component Tests', () => {
    describe('InfoModel Management Detail Component', () => {
        let comp: InfoModelDetailComponent;
        let fixture: ComponentFixture<InfoModelDetailComponent>;
        const route = ({ data: of({ infoModel: new InfoModel('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CatalogValidatorTestModule],
                declarations: [InfoModelDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(InfoModelDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(InfoModelDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.infoModel).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
