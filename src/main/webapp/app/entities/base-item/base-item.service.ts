import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBaseItem } from 'app/shared/model/base-item.model';

type EntityResponseType = HttpResponse<IBaseItem>;
type EntityArrayResponseType = HttpResponse<IBaseItem[]>;

@Injectable({ providedIn: 'root' })
export class BaseItemService {
    public resourceUrl = SERVER_API_URL + 'api/base-items';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/base-items';

    constructor(protected http: HttpClient) {}

    create(baseItem: IBaseItem): Observable<EntityResponseType> {
        return this.http.post<IBaseItem>(this.resourceUrl, baseItem, { observe: 'response' });
    }

    update(baseItem: IBaseItem): Observable<EntityResponseType> {
        return this.http.put<IBaseItem>(this.resourceUrl, baseItem, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IBaseItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBaseItem[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBaseItem[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
