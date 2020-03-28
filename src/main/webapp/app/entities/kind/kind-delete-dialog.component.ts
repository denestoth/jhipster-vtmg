import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IKind } from 'app/shared/model/kind.model';
import { KindService } from './kind.service';

@Component({
  templateUrl: './kind-delete-dialog.component.html'
})
export class KindDeleteDialogComponent {
  kind?: IKind;

  constructor(protected kindService: KindService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.kindService.delete(id).subscribe(() => {
      this.eventManager.broadcast('kindListModification');
      this.activeModal.close();
    });
  }
}
