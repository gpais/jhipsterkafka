import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { VisitorCardTransactions } from './visitor-card-transactions.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class VisitorCardTransactionsService {

    private resourceUrl = 'visitors/api/visitor-card-transactions';

    constructor(private http: Http) { }

    create(visitorCardTransactions: VisitorCardTransactions): Observable<VisitorCardTransactions> {
        const copy = this.convert(visitorCardTransactions);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(visitorCardTransactions: VisitorCardTransactions): Observable<VisitorCardTransactions> {
        const copy = this.convert(visitorCardTransactions);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<VisitorCardTransactions> {
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

    private convert(visitorCardTransactions: VisitorCardTransactions): VisitorCardTransactions {
        const copy: VisitorCardTransactions = Object.assign({}, visitorCardTransactions);
        return copy;
    }
}
