import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Characteristic } from 'app/shared/model/characteristic.model';
import { CharacteristicService } from './characteristic.service';
import { CharacteristicComponent } from './characteristic.component';
import { CharacteristicDetailComponent } from './characteristic-detail.component';
import { CharacteristicUpdateComponent } from './characteristic-update.component';
import { CharacteristicDeletePopupComponent } from './characteristic-delete-dialog.component';
import { ICharacteristic } from 'app/shared/model/characteristic.model';

@Injectable({ providedIn: 'root' })
export class CharacteristicResolve implements Resolve<ICharacteristic> {
    constructor(private service: CharacteristicService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Characteristic> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Characteristic>) => response.ok),
                map((characteristic: HttpResponse<Characteristic>) => characteristic.body)
            );
        }
        return of(new Characteristic());
    }
}

export const characteristicRoute: Routes = [
    {
        path: 'characteristic',
        component: CharacteristicComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.characteristic.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'characteristic/:id/view',
        component: CharacteristicDetailComponent,
        resolve: {
            characteristic: CharacteristicResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.characteristic.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'characteristic/new',
        component: CharacteristicUpdateComponent,
        resolve: {
            characteristic: CharacteristicResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.characteristic.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'characteristic/:id/edit',
        component: CharacteristicUpdateComponent,
        resolve: {
            characteristic: CharacteristicResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.characteristic.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const characteristicPopupRoute: Routes = [
    {
        path: 'characteristic/:id/delete',
        component: CharacteristicDeletePopupComponent,
        resolve: {
            characteristic: CharacteristicResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'catalogValidatorApp.characteristic.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
