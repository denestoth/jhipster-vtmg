import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IKind, Kind } from 'app/shared/model/kind.model';
import { KindService } from './kind.service';
import { KindComponent } from './kind.component';
import { KindDetailComponent } from './kind-detail.component';
import { KindUpdateComponent } from './kind-update.component';

@Injectable({ providedIn: 'root' })
export class KindResolve implements Resolve<IKind> {
  constructor(private service: KindService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IKind> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((kind: HttpResponse<Kind>) => {
          if (kind.body) {
            return of(kind.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Kind());
  }
}

export const kindRoute: Routes = [
  {
    path: '',
    component: KindComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'vtmgApp.kind.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: KindDetailComponent,
    resolve: {
      kind: KindResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'vtmgApp.kind.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: KindUpdateComponent,
    resolve: {
      kind: KindResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'vtmgApp.kind.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: KindUpdateComponent,
    resolve: {
      kind: KindResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'vtmgApp.kind.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
