import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPerson, Person } from 'app/shared/model/person.model';
import { PersonService } from './person.service';
import { IKind } from 'app/shared/model/kind.model';
import { KindService } from 'app/entities/kind/kind.service';

@Component({
  selector: 'jhi-person-update',
  templateUrl: './person-update.component.html'
})
export class PersonUpdateComponent implements OnInit {
  isSaving = false;
  kinds: IKind[] = [];
  bithDateDp: any;
  deathDateDp: any;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    bithDate: [],
    deathDate: [],
    details: [],
    gender: [],
    kind: []
  });

  constructor(
    protected personService: PersonService,
    protected kindService: KindService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ person }) => {
      this.updateForm(person);

      this.kindService.query().subscribe((res: HttpResponse<IKind[]>) => (this.kinds = res.body || []));
    });
  }

  updateForm(person: IPerson): void {
    this.editForm.patchValue({
      id: person.id,
      name: person.name,
      bithDate: person.bithDate,
      deathDate: person.deathDate,
      details: person.details,
      gender: person.gender,
      kind: person.kind
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const person = this.createFromForm();
    if (person.id !== undefined) {
      this.subscribeToSaveResponse(this.personService.update(person));
    } else {
      this.subscribeToSaveResponse(this.personService.create(person));
    }
  }

  private createFromForm(): IPerson {
    return {
      ...new Person(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      bithDate: this.editForm.get(['bithDate'])!.value,
      deathDate: this.editForm.get(['deathDate'])!.value,
      details: this.editForm.get(['details'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      kind: this.editForm.get(['kind'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPerson>>): void {
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

  trackById(index: number, item: IKind): any {
    return item.id;
  }
}
