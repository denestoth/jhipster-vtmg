import { Moment } from 'moment';
import { IKind } from 'app/shared/model/kind.model';
import { IEvent } from 'app/shared/model/event.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';

export interface IPerson {
  id?: string;
  name?: string;
  bithDate?: Moment;
  deathDate?: Moment;
  details?: string;
  gender?: Gender;
  kind?: IKind;
  events?: IEvent[];
}

export class Person implements IPerson {
  constructor(
    public id?: string,
    public name?: string,
    public bithDate?: Moment,
    public deathDate?: Moment,
    public details?: string,
    public gender?: Gender,
    public kind?: IKind,
    public events?: IEvent[]
  ) {}
}
