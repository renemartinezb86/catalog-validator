import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TaxObject } from 'app/shared/model/tax-object.model';
import { TaxObjectService } from './tax-object.service';
import { TaxObjectComponent } from './tax-object.component';
import { TaxObjectDetailComponent } from './tax-object-detail.component';
import { TaxObjectUpdateComponent } from './tax-object-update.component';
import { TaxObjectDeletePopupComponent } from './tax-object-delete-dialog.component';
import { ITaxObject } from 'app/shared/model/tax-object.model';

@Injectable({ providedIn: 'root' })
export class TaxObjectResolve implements Resolve<ITaxObject> {
    constructor(private service: TaxObjectService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<TaxObject> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TaxObject>) => response.ok),
                map((taxObject: HttpResponse<TaxObject>) => taxObject.body)
            );
        }
        return of(new TaxObject());
    }
}

export const taxObjectRoute: Routes = [
    {
        path: 'tax-object',
        component: TaxObjectComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.taxObject.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tax-object/:id/view',
        component: TaxObjectDetailComponent,
        resolve: {
            taxObject: TaxObjectResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.taxObject.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tax-object/new',
        component: TaxObjectUpdateComponent,
        resolve: {
            taxObject: TaxObjectResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.taxObject.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tax-object/:id/edit',
        component: TaxObjectUpdateComponent,
        resolve: {
            taxObject: TaxObjectResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.taxObject.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const taxObjectPopupRoute: Routes = [
    {
        path: 'tax-object/:id/delete',
        component: TaxObjectDeletePopupComponent,
        resolve: {
            taxObject: TaxObjectResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.taxObject.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
