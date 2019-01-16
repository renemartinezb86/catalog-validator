import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { InfoModelAttr } from 'app/shared/model/info-model-attr.model';
import { InfoModelAttrService } from './info-model-attr.service';
import { InfoModelAttrComponent } from './info-model-attr.component';
import { InfoModelAttrDetailComponent } from './info-model-attr-detail.component';
import { InfoModelAttrUpdateComponent } from './info-model-attr-update.component';
import { InfoModelAttrDeletePopupComponent } from './info-model-attr-delete-dialog.component';
import { IInfoModelAttr } from 'app/shared/model/info-model-attr.model';

@Injectable({ providedIn: 'root' })
export class InfoModelAttrResolve implements Resolve<IInfoModelAttr> {
    constructor(private service: InfoModelAttrService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<InfoModelAttr> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<InfoModelAttr>) => response.ok),
                map((infoModelAttr: HttpResponse<InfoModelAttr>) => infoModelAttr.body)
            );
        }
        return of(new InfoModelAttr());
    }
}

export const infoModelAttrRoute: Routes = [
    {
        path: 'info-model-attr',
        component: InfoModelAttrComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.infoModelAttr.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'info-model-attr/:id/view',
        component: InfoModelAttrDetailComponent,
        resolve: {
            infoModelAttr: InfoModelAttrResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.infoModelAttr.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'info-model-attr/new',
        component: InfoModelAttrUpdateComponent,
        resolve: {
            infoModelAttr: InfoModelAttrResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.infoModelAttr.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'info-model-attr/:id/edit',
        component: InfoModelAttrUpdateComponent,
        resolve: {
            infoModelAttr: InfoModelAttrResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.infoModelAttr.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const infoModelAttrPopupRoute: Routes = [
    {
        path: 'info-model-attr/:id/delete',
        component: InfoModelAttrDeletePopupComponent,
        resolve: {
            infoModelAttr: InfoModelAttrResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.infoModelAttr.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
