import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITaxModel } from 'app/shared/model/tax-model.model';

type EntityResponseType = HttpResponse<ITaxModel>;
type EntityArrayResponseType = HttpResponse<ITaxModel[]>;

@Injectable({ providedIn: 'root' })
export class TaxModelService {
    public resourceUrl = SERVER_API_URL + 'api/tax-models';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/tax-models';

    constructor(protected http: HttpClient) {}

    create(taxModel: ITaxModel): Observable<EntityResponseType> {
        return this.http.post<ITaxModel>(this.resourceUrl, taxModel, { observe: 'response' });
    }

    update(taxModel: ITaxModel): Observable<EntityResponseType> {
        return this.http.put<ITaxModel>(this.resourceUrl, taxModel, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<ITaxModel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITaxModel[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITaxModel[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
