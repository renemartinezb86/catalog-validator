import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { InfoModel } from 'app/shared/model/info-model.model';
import { InfoModelService } from './info-model.service';
import { InfoModelComponent } from './info-model.component';
import { InfoModelDetailComponent } from './info-model-detail.component';
import { InfoModelUpdateComponent } from './info-model-update.component';
import { InfoModelDeletePopupComponent } from './info-model-delete-dialog.component';
import { IInfoModel } from 'app/shared/model/info-model.model';

@Injectable({ providedIn: 'root' })
export class InfoModelResolve implements Resolve<IInfoModel> {
    constructor(private service: InfoModelService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<InfoModel> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<InfoModel>) => response.ok),
                map((infoModel: HttpResponse<InfoModel>) => infoModel.body)
            );
        }
        return of(new InfoModel());
    }
}

export const infoModelRoute: Routes = [
    {
        path: 'info-model',
        component: InfoModelComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.infoModel.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'info-model/:id/view',
        component: InfoModelDetailComponent,
        resolve: {
            infoModel: InfoModelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.infoModel.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'info-model/new',
        component: InfoModelUpdateComponent,
        resolve: {
            infoModel: InfoModelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.infoModel.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'info-model/:id/edit',
        component: InfoModelUpdateComponent,
        resolve: {
            infoModel: InfoModelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.infoModel.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const infoModelPopupRoute: Routes = [
    {
        path: 'info-model/:id/delete',
        component: InfoModelDeletePopupComponent,
        resolve: {
            infoModel: InfoModelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.infoModel.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
