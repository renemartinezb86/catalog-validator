import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SimpleItem } from 'app/shared/model/simple-item.model';
import { SimpleItemService } from './simple-item.service';
import { SimpleItemComponent } from './simple-item.component';
import { SimpleItemDetailComponent } from './simple-item-detail.component';
import { SimpleItemUpdateComponent } from './simple-item-update.component';
import { SimpleItemDeletePopupComponent } from './simple-item-delete-dialog.component';
import { ISimpleItem } from 'app/shared/model/simple-item.model';

@Injectable({ providedIn: 'root' })
export class SimpleItemResolve implements Resolve<ISimpleItem> {
    constructor(private service: SimpleItemService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<SimpleItem> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SimpleItem>) => response.ok),
                map((simpleItem: HttpResponse<SimpleItem>) => simpleItem.body)
            );
        }
        return of(new SimpleItem());
    }
}

export const simpleItemRoute: Routes = [
    {
        path: 'simple-item',
        component: SimpleItemComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.simpleItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'simple-item/:id/view',
        component: SimpleItemDetailComponent,
        resolve: {
            simpleItem: SimpleItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.simpleItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'simple-item/new',
        component: SimpleItemUpdateComponent,
        resolve: {
            simpleItem: SimpleItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.simpleItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'simple-item/:id/edit',
        component: SimpleItemUpdateComponent,
        resolve: {
            simpleItem: SimpleItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.simpleItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const simpleItemPopupRoute: Routes = [
    {
        path: 'simple-item/:id/delete',
        component: SimpleItemDeletePopupComponent,
        resolve: {
            simpleItem: SimpleItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.simpleItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
