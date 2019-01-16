import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInfoModelAttr } from 'app/shared/model/info-model-attr.model';

type EntityResponseType = HttpResponse<IInfoModelAttr>;
type EntityArrayResponseType = HttpResponse<IInfoModelAttr[]>;

@Injectable({ providedIn: 'root' })
export class InfoModelAttrService {
    public resourceUrl = SERVER_API_URL + 'api/info-model-attrs';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/info-model-attrs';

    constructor(protected http: HttpClient) {}

    create(infoModelAttr: IInfoModelAttr): Observable<EntityResponseType> {
        return this.http.post<IInfoModelAttr>(this.resourceUrl, infoModelAttr, { observe: 'response' });
    }

    update(infoModelAttr: IInfoModelAttr): Observable<EntityResponseType> {
        return this.http.put<IInfoModelAttr>(this.resourceUrl, infoModelAttr, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IInfoModelAttr>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IInfoModelAttr[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IInfoModelAttr[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
