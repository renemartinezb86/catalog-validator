import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITaxObject } from 'app/shared/model/tax-object.model';

type EntityResponseType = HttpResponse<ITaxObject>;
type EntityArrayResponseType = HttpResponse<ITaxObject[]>;

@Injectable({ providedIn: 'root' })
export class TaxObjectService {
    public resourceUrl = SERVER_API_URL + 'api/tax-objects';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/tax-objects';

    constructor(protected http: HttpClient) {}

    create(taxObject: ITaxObject): Observable<EntityResponseType> {
        return this.http.post<ITaxObject>(this.resourceUrl, taxObject, { observe: 'response' });
    }

    update(taxObject: ITaxObject): Observable<EntityResponseType> {
        return this.http.put<ITaxObject>(this.resourceUrl, taxObject, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<ITaxObject>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITaxObject[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITaxObject[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
