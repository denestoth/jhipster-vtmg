import { IPerson } from 'app/shared/model/person.model';

export interface IKind {
  id?: string;
  description?: string;
  details?: string;
  people?: IPerson[];
}

export class Kind implements IKind {
  constructor(public id?: string, public description?: string, public details?: string, public people?: IPerson[]) {}
}
