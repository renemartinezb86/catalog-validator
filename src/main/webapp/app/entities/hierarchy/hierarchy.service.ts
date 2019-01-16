import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IHierarchy } from 'app/shared/model/hierarchy.model';

type EntityResponseType = HttpResponse<IHierarchy>;
type EntityArrayResponseType = HttpResponse<IHierarchy[]>;

@Injectable({ providedIn: 'root' })
export class HierarchyService {
    public resourceUrl = SERVER_API_URL + 'api/hierarchies';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/hierarchies';

    constructor(protected http: HttpClient) {}

    create(hierarchy: IHierarchy): Observable<EntityResponseType> {
        return this.http.post<IHierarchy>(this.resourceUrl, hierarchy, { observe: 'response' });
    }

    update(hierarchy: IHierarchy): Observable<EntityResponseType> {
        return this.http.put<IHierarchy>(this.resourceUrl, hierarchy, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IHierarchy>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IHierarchy[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IHierarchy[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
