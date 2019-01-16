import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IIND } from 'app/shared/model/ind.model';

type EntityResponseType = HttpResponse<IIND>;
type EntityArrayResponseType = HttpResponse<IIND[]>;

@Injectable({ providedIn: 'root' })
export class INDService {
    public resourceUrl = SERVER_API_URL + 'api/inds';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/inds';

    constructor(protected http: HttpClient) {}

    create(iND: IIND): Observable<EntityResponseType> {
        return this.http.post<IIND>(this.resourceUrl, iND, { observe: 'response' });
    }

    update(iND: IIND): Observable<EntityResponseType> {
        return this.http.put<IIND>(this.resourceUrl, iND, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IIND>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IIND[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IIND[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
