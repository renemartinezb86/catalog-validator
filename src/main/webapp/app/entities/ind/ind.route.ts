import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IND } from 'app/shared/model/ind.model';
import { INDService } from './ind.service';
import { INDComponent } from './ind.component';
import { INDDetailComponent } from './ind-detail.component';
import { INDUpdateComponent } from './ind-update.component';
import { INDDeletePopupComponent } from './ind-delete-dialog.component';
import { IIND } from 'app/shared/model/ind.model';

@Injectable({ providedIn: 'root' })
export class INDResolve implements Resolve<IIND> {
    constructor(private service: INDService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IND> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<IND>) => response.ok),
                map((iND: HttpResponse<IND>) => iND.body)
            );
        }
        return of(new IND());
    }
}

export const iNDRoute: Routes = [
    {
        path: 'ind',
        component: INDComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.iND.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ind/:id/view',
        component: INDDetailComponent,
        resolve: {
            iND: INDResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.iND.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ind/new',
        component: INDUpdateComponent,
        resolve: {
            iND: INDResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.iND.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ind/:id/edit',
        component: INDUpdateComponent,
        resolve: {
            iND: INDResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.iND.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const iNDPopupRoute: Routes = [
    {
        path: 'ind/:id/delete',
        component: INDDeletePopupComponent,
        resolve: {
            iND: INDResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.iND.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
