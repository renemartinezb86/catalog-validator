/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { CharacteristicService } from 'app/entities/characteristic/characteristic.service';
import { ICharacteristic, Characteristic } from 'app/shared/model/characteristic.model';

describe('Service Tests', () => {
    describe('Characteristic Service', () => {
        let injector: TestBed;
        let service: CharacteristicService;
        let httpMock: HttpTestingController;
        let elemDefault: ICharacteristic;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(CharacteristicService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new Characteristic(
                'ID',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA'
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find('123')
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Characteristic', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 'ID'
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new Characteristic(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Characteristic', async () => {
                const returnedFromService = Object.assign(
                    {
                        charID: 'BBBBBB',
                        charName: 'BBBBBB',
                        charType: 'BBBBBB',
                        defaultValue: 'BBBBBB',
                        charClassificationType: 'BBBBBB',
                        sequence: 'BBBBBB',
                        assocType: 'BBBBBB',
                        promoted: 'BBBBBB',
                        sourceItem: 'BBBBBB',
                        charDefaultRule: 'BBBBBB',
                        mapto: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Characteristic', async () => {
                const returnedFromService = Object.assign(
                    {
                        charID: 'BBBBBB',
                        charName: 'BBBBBB',
                        charType: 'BBBBBB',
                        defaultValue: 'BBBBBB',
                        charClassificationType: 'BBBBBB',
                        sequence: 'BBBBBB',
                        assocType: 'BBBBBB',
                        promoted: 'BBBBBB',
                        sourceItem: 'BBBBBB',
                        charDefaultRule: 'BBBBBB',
                        mapto: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Characteristic', async () => {
                const rxPromise = service.delete('123').subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
