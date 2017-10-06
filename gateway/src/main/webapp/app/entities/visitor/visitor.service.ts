import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Visitor } from './visitor.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class VisitorService {

    private resourceUrl = 'visitors/api/visitors';

    constructor(private http: Http) { }

    create(visitor: Visitor): Observable<Visitor> {
        const copy = this.convert(visitor);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(visitor: Visitor): Observable<Visitor> {
        const copy = this.convert(visitor);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Visitor> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(visitor: Visitor): Visitor {
        const copy: Visitor = Object.assign({}, visitor);
        return copy;
    }
}
