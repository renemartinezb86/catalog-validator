import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Hierarchy } from 'app/shared/model/hierarchy.model';
import { HierarchyService } from './hierarchy.service';
import { HierarchyComponent } from './hierarchy.component';
import { HierarchyDetailComponent } from './hierarchy-detail.component';
import { HierarchyUpdateComponent } from './hierarchy-update.component';
import { HierarchyDeletePopupComponent } from './hierarchy-delete-dialog.component';
import { IHierarchy } from 'app/shared/model/hierarchy.model';

@Injectable({ providedIn: 'root' })
export class HierarchyResolve implements Resolve<IHierarchy> {
    constructor(private service: HierarchyService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Hierarchy> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Hierarchy>) => response.ok),
                map((hierarchy: HttpResponse<Hierarchy>) => hierarchy.body)
            );
        }
        return of(new Hierarchy());
    }
}

export const hierarchyRoute: Routes = [
    {
        path: 'hierarchy',
        component: HierarchyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.hierarchy.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'hierarchy/:id/view',
        component: HierarchyDetailComponent,
        resolve: {
            hierarchy: HierarchyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.hierarchy.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'hierarchy/new',
        component: HierarchyUpdateComponent,
        resolve: {
            hierarchy: HierarchyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.hierarchy.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'hierarchy/:id/edit',
        component: HierarchyUpdateComponent,
        resolve: {
            hierarchy: HierarchyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.hierarchy.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const hierarchyPopupRoute: Routes = [
    {
        path: 'hierarchy/:id/delete',
        component: HierarchyDeletePopupComponent,
        resolve: {
            hierarchy: HierarchyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.hierarchy.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
