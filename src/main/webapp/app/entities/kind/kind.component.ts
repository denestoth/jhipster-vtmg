import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IKind } from 'app/shared/model/kind.model';
import { KindService } from './kind.service';
import { KindDeleteDialogComponent } from './kind-delete-dialog.component';

@Component({
  selector: 'jhi-kind',
  templateUrl: './kind.component.html'
})
export class KindComponent implements OnInit, OnDestroy {
  kinds?: IKind[];
  eventSubscriber?: Subscription;

  constructor(protected kindService: KindService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.kindService.query().subscribe((res: HttpResponse<IKind[]>) => (this.kinds = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInKinds();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IKind): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInKinds(): void {
    this.eventSubscriber = this.eventManager.subscribe('kindListModification', () => this.loadAll());
  }

  delete(kind: IKind): void {
    const modalRef = this.modalService.open(KindDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.kind = kind;
  }
}
