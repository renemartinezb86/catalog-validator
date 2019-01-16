import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICatalog } from 'app/shared/model/catalog.model';

type EntityResponseType = HttpResponse<ICatalog>;
type EntityArrayResponseType = HttpResponse<ICatalog[]>;

@Injectable({ providedIn: 'root' })
export class CatalogService {
    public resourceUrl = SERVER_API_URL + 'api/catalogs';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/catalogs';

    constructor(protected http: HttpClient) {}

    create(catalog: ICatalog): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(catalog);
        return this.http
            .post<ICatalog>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(catalog: ICatalog): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(catalog);
        return this.http
            .put<ICatalog>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http
            .get<ICatalog>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICatalog[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICatalog[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(catalog: ICatalog): ICatalog {
        const copy: ICatalog = Object.assign({}, catalog, {
            createdDate: catalog.createdDate != null && catalog.createdDate.isValid() ? catalog.createdDate.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((catalog: ICatalog) => {
                catalog.createdDate = catalog.createdDate != null ? moment(catalog.createdDate) : null;
            });
        }
        return res;
    }
}
