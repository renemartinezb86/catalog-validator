import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Validation } from 'app/shared/model/validation.model';
import { ValidationService } from './validation.service';
import { ValidationComponent } from './validation.component';
import { ValidationDetailComponent } from './validation-detail.component';
import { ValidationUpdateComponent } from './validation-update.component';
import { ValidationDeletePopupComponent } from './validation-delete-dialog.component';
import { IValidation } from 'app/shared/model/validation.model';

@Injectable({ providedIn: 'root' })
export class ValidationResolve implements Resolve<IValidation> {
    constructor(private service: ValidationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Validation> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Validation>) => response.ok),
                map((validation: HttpResponse<Validation>) => validation.body)
            );
        }
        return of(new Validation());
    }
}

export const validationRoute: Routes = [
    {
        path: 'validation',
        component: ValidationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.validation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'validation/:id/view',
        component: ValidationDetailComponent,
        resolve: {
            validation: ValidationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.validation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'validation/new',
        component: ValidationUpdateComponent,
        resolve: {
            validation: ValidationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.validation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'validation/:id/edit',
        component: ValidationUpdateComponent,
        resolve: {
            validation: ValidationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.validation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const validationPopupRoute: Routes = [
    {
        path: 'validation/:id/delete',
        component: ValidationDeletePopupComponent,
        resolve: {
            validation: ValidationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.validation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
