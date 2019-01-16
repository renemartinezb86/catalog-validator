/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CatalogValidatorTestModule } from '../../../test.module';
import { CharacteristicDetailComponent } from 'app/entities/characteristic/characteristic-detail.component';
import { Characteristic } from 'app/shared/model/characteristic.model';

describe('Component Tests', () => {
    describe('Characteristic Management Detail Component', () => {
        let comp: CharacteristicDetailComponent;
        let fixture: ComponentFixture<CharacteristicDetailComponent>;
        const route = ({ data: of({ characteristic: new Characteristic('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CatalogValidatorTestModule],
                declarations: [CharacteristicDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CharacteristicDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CharacteristicDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.characteristic).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
