/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { InfoModelAttrService } from 'app/entities/info-model-attr/info-model-attr.service';
import { IInfoModelAttr, InfoModelAttr } from 'app/shared/model/info-model-attr.model';

describe('Service Tests', () => {
    describe('InfoModelAttr Service', () => {
        let injector: TestBed;
        let service: InfoModelAttrService;
        let httpMock: HttpTestingController;
        let elemDefault: IInfoModelAttr;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(InfoModelAttrService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new InfoModelAttr(
                'ID',
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

            it('should create a InfoModelAttr', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 'ID'
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new InfoModelAttr(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a InfoModelAttr', async () => {
                const returnedFromService = Object.assign(
                    {
                        attrCode: 'BBBBBB',
                        charType: 'BBBBBB',
                        name: 'BBBBBB',
                        isNull: 'BBBBBB',
                        isSearch: 'BBBBBB',
                        descending: 'BBBBBB',
                        type: 'BBBBBB',
                        assoctype: 'BBBBBB',
                        seq: 'BBBBBB'
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

            it('should return a list of InfoModelAttr', async () => {
                const returnedFromService = Object.assign(
                    {
                        attrCode: 'BBBBBB',
                        charType: 'BBBBBB',
                        name: 'BBBBBB',
                        isNull: 'BBBBBB',
                        isSearch: 'BBBBBB',
                        descending: 'BBBBBB',
                        type: 'BBBBBB',
                        assoctype: 'BBBBBB',
                        seq: 'BBBBBB'
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

            it('should delete a InfoModelAttr', async () => {
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
