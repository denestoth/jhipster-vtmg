import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IKind } from 'app/shared/model/kind.model';

type EntityResponseType = HttpResponse<IKind>;
type EntityArrayResponseType = HttpResponse<IKind[]>;

@Injectable({ providedIn: 'root' })
export class KindService {
  public resourceUrl = SERVER_API_URL + 'api/kinds';

  constructor(protected http: HttpClient) {}

  create(kind: IKind): Observable<EntityResponseType> {
    return this.http.post<IKind>(this.resourceUrl, kind, { observe: 'response' });
  }

  update(kind: IKind): Observable<EntityResponseType> {
    return this.http.put<IKind>(this.resourceUrl, kind, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IKind>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IKind[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
