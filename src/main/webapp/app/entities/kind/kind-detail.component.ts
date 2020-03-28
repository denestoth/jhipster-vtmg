import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKind } from 'app/shared/model/kind.model';

@Component({
  selector: 'jhi-kind-detail',
  templateUrl: './kind-detail.component.html'
})
export class KindDetailComponent implements OnInit {
  kind: IKind | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kind }) => (this.kind = kind));
  }

  previousState(): void {
    window.history.back();
  }
}
