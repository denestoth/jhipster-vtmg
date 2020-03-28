import { Moment } from 'moment';
import { IPerson } from 'app/shared/model/person.model';
import { IPlace } from 'app/shared/model/place.model';

export interface IEvent {
  id?: string;
  description?: string;
  date?: Moment;
  details?: string;
  people?: IPerson[];
  place?: IPlace;
}

export class Event implements IEvent {
  constructor(
    public id?: string,
    public description?: string,
    public date?: Moment,
    public details?: string,
    public people?: IPerson[],
    public place?: IPlace
  ) {}
}
