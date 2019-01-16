import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISimpleItem } from 'app/shared/model/simple-item.model';

type EntityResponseType = HttpResponse<ISimpleItem>;
type EntityArrayResponseType = HttpResponse<ISimpleItem[]>;

@Injectable({ providedIn: 'root' })
export class SimpleItemService {
    public resourceUrl = SERVER_API_URL + 'api/simple-items';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/simple-items';

    constructor(protected http: HttpClient) {}

    create(simpleItem: ISimpleItem): Observable<EntityResponseType> {
        return this.http.post<ISimpleItem>(this.resourceUrl, simpleItem, { observe: 'response' });
    }

    update(simpleItem: ISimpleItem): Observable<EntityResponseType> {
        return this.http.put<ISimpleItem>(this.resourceUrl, simpleItem, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<ISimpleItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISimpleItem[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISimpleItem[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
