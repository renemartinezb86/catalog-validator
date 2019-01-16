import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInfoModel } from 'app/shared/model/info-model.model';

type EntityResponseType = HttpResponse<IInfoModel>;
type EntityArrayResponseType = HttpResponse<IInfoModel[]>;

@Injectable({ providedIn: 'root' })
export class InfoModelService {
    public resourceUrl = SERVER_API_URL + 'api/info-models';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/info-models';

    constructor(protected http: HttpClient) {}

    create(infoModel: IInfoModel): Observable<EntityResponseType> {
        return this.http.post<IInfoModel>(this.resourceUrl, infoModel, { observe: 'response' });
    }

    update(infoModel: IInfoModel): Observable<EntityResponseType> {
        return this.http.put<IInfoModel>(this.resourceUrl, infoModel, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IInfoModel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IInfoModel[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IInfoModel[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
