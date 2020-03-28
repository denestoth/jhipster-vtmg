import { IEvent } from 'app/shared/model/event.model';

export interface IPlace {
  id?: string;
  name?: string;
  details?: string;
  events?: IEvent[];
}

export class Place implements IPlace {
  constructor(public id?: string, public name?: string, public details?: string, public events?: IEvent[]) {}
}
