import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BaseItem } from 'app/shared/model/base-item.model';
import { BaseItemService } from './base-item.service';
import { BaseItemComponent } from './base-item.component';
import { BaseItemDetailComponent } from './base-item-detail.component';
import { BaseItemUpdateComponent } from './base-item-update.component';
import { BaseItemDeletePopupComponent } from './base-item-delete-dialog.component';
import { IBaseItem } from 'app/shared/model/base-item.model';

@Injectable({ providedIn: 'root' })
export class BaseItemResolve implements Resolve<IBaseItem> {
    constructor(private service: BaseItemService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<BaseItem> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<BaseItem>) => response.ok),
                map((baseItem: HttpResponse<BaseItem>) => baseItem.body)
            );
        }
        return of(new BaseItem());
    }
}

export const baseItemRoute: Routes = [
    {
        path: 'base-item',
        component: BaseItemComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.baseItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'base-item/:id/view',
        component: BaseItemDetailComponent,
        resolve: {
            baseItem: BaseItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.baseItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'base-item/new',
        component: BaseItemUpdateComponent,
        resolve: {
            baseItem: BaseItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.baseItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'base-item/:id/edit',
        component: BaseItemUpdateComponent,
        resolve: {
            baseItem: BaseItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.baseItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const baseItemPopupRoute: Routes = [
    {
        path: 'base-item/:id/delete',
        component: BaseItemDeletePopupComponent,
        resolve: {
            baseItem: BaseItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.baseItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
