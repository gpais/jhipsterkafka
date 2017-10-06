import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Retailers } from './retailers.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RetailersService {

    private resourceUrl = 'retailers/api/retailers';

    constructor(private http: Http) { }

    create(retailers: Retailers): Observable<Retailers> {
        const copy = this.convert(retailers);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(retailers: Retailers): Observable<Retailers> {
        const copy = this.convert(retailers);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Retailers> {
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

    private convert(retailers: Retailers): Retailers {
        const copy: Retailers = Object.assign({}, retailers);
        return copy;
    }
}
