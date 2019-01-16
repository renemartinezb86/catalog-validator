import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICharacteristic } from 'app/shared/model/characteristic.model';

type EntityResponseType = HttpResponse<ICharacteristic>;
type EntityArrayResponseType = HttpResponse<ICharacteristic[]>;

@Injectable({ providedIn: 'root' })
export class CharacteristicService {
    public resourceUrl = SERVER_API_URL + 'api/characteristics';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/characteristics';

    constructor(protected http: HttpClient) {}

    create(characteristic: ICharacteristic): Observable<EntityResponseType> {
        return this.http.post<ICharacteristic>(this.resourceUrl, characteristic, { observe: 'response' });
    }

    update(characteristic: ICharacteristic): Observable<EntityResponseType> {
        return this.http.put<ICharacteristic>(this.resourceUrl, characteristic, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<ICharacteristic>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICharacteristic[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICharacteristic[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
