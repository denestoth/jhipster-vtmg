import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IKind, Kind } from 'app/shared/model/kind.model';
import { KindService } from './kind.service';

@Component({
  selector: 'jhi-kind-update',
  templateUrl: './kind-update.component.html'
})
export class KindUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    description: [null, [Validators.required]],
    details: []
  });

  constructor(protected kindService: KindService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kind }) => {
      this.updateForm(kind);
    });
  }

  updateForm(kind: IKind): void {
    this.editForm.patchValue({
      id: kind.id,
      description: kind.description,
      details: kind.details
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const kind = this.createFromForm();
    if (kind.id !== undefined) {
      this.subscribeToSaveResponse(this.kindService.update(kind));
    } else {
      this.subscribeToSaveResponse(this.kindService.create(kind));
    }
  }

  private createFromForm(): IKind {
    return {
      ...new Kind(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      details: this.editForm.get(['details'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKind>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
