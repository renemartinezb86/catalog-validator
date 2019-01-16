import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IValidation } from 'app/shared/model/validation.model';

type EntityResponseType = HttpResponse<IValidation>;
type EntityArrayResponseType = HttpResponse<IValidation[]>;

@Injectable({ providedIn: 'root' })
export class ValidationService {
    public resourceUrl = SERVER_API_URL + 'api/validations';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/validations';

    constructor(protected http: HttpClient) {}

    create(validation: IValidation): Observable<EntityResponseType> {
        return this.http.post<IValidation>(this.resourceUrl, validation, { observe: 'response' });
    }

    update(validation: IValidation): Observable<EntityResponseType> {
        return this.http.put<IValidation>(this.resourceUrl, validation, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IValidation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IValidation[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IValidation[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
