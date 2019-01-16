import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TaxModel } from 'app/shared/model/tax-model.model';
import { TaxModelService } from './tax-model.service';
import { TaxModelComponent } from './tax-model.component';
import { TaxModelDetailComponent } from './tax-model-detail.component';
import { TaxModelUpdateComponent } from './tax-model-update.component';
import { TaxModelDeletePopupComponent } from './tax-model-delete-dialog.component';
import { ITaxModel } from 'app/shared/model/tax-model.model';

@Injectable({ providedIn: 'root' })
export class TaxModelResolve implements Resolve<ITaxModel> {
    constructor(private service: TaxModelService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<TaxModel> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TaxModel>) => response.ok),
                map((taxModel: HttpResponse<TaxModel>) => taxModel.body)
            );
        }
        return of(new TaxModel());
    }
}

export const taxModelRoute: Routes = [
    {
        path: 'tax-model',
        component: TaxModelComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.taxModel.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tax-model/:id/view',
        component: TaxModelDetailComponent,
        resolve: {
            taxModel: TaxModelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.taxModel.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tax-model/new',
        component: TaxModelUpdateComponent,
        resolve: {
            taxModel: TaxModelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.taxModel.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tax-model/:id/edit',
        component: TaxModelUpdateComponent,
        resolve: {
            taxModel: TaxModelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.taxModel.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const taxModelPopupRoute: Routes = [
    {
        path: 'tax-model/:id/delete',
        component: TaxModelDeletePopupComponent,
        resolve: {
            taxModel: TaxModelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.taxModel.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
