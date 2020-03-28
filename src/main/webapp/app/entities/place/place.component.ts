import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPlace } from 'app/shared/model/place.model';
import { PlaceService } from './place.service';
import { PlaceDeleteDialogComponent } from './place-delete-dialog.component';

@Component({
  selector: 'jhi-place',
  templateUrl: './place.component.html'
})
export class PlaceComponent implements OnInit, OnDestroy {
  places?: IPlace[];
  eventSubscriber?: Subscription;

  constructor(protected placeService: PlaceService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.placeService.query().subscribe((res: HttpResponse<IPlace[]>) => (this.places = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPlaces();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPlace): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPlaces(): void {
    this.eventSubscriber = this.eventManager.subscribe('placeListModification', () => this.loadAll());
  }

  delete(place: IPlace): void {
    const modalRef = this.modalService.open(PlaceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.place = place;
  }
}
