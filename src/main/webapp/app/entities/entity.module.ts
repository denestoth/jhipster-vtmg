import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'person',
        loadChildren: () => import('./person/person.module').then(m => m.VtmgPersonModule)
      },
      {
        path: 'kind',
        loadChildren: () => import('./kind/kind.module').then(m => m.VtmgKindModule)
      },
      {
        path: 'place',
        loadChildren: () => import('./place/place.module').then(m => m.VtmgPlaceModule)
      },
      {
        path: 'event',
        loadChildren: () => import('./event/event.module').then(m => m.VtmgEventModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class VtmgEntityModule {}
