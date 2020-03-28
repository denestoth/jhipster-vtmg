import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VtmgSharedModule } from 'app/shared/shared.module';
import { KindComponent } from './kind.component';
import { KindDetailComponent } from './kind-detail.component';
import { KindUpdateComponent } from './kind-update.component';
import { KindDeleteDialogComponent } from './kind-delete-dialog.component';
import { kindRoute } from './kind.route';

@NgModule({
  imports: [VtmgSharedModule, RouterModule.forChild(kindRoute)],
  declarations: [KindComponent, KindDetailComponent, KindUpdateComponent, KindDeleteDialogComponent],
  entryComponents: [KindDeleteDialogComponent]
})
export class VtmgKindModule {}
