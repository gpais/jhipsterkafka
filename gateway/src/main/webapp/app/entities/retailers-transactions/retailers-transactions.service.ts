import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { RetailersTransactions } from './retailers-transactions.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RetailersTransactionsService {

    private resourceUrl = 'retailers/api/retailers-transactions';

    constructor(private http: Http) { }

    create(retailersTransactions: RetailersTransactions): Observable<RetailersTransactions> {
        const copy = this.convert(retailersTransactions);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(retailersTransactions: RetailersTransactions): Observable<RetailersTransactions> {
        const copy = this.convert(retailersTransactions);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<RetailersTransactions> {
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

    private convert(retailersTransactions: RetailersTransactions): RetailersTransactions {
        const copy: RetailersTransactions = Object.assign({}, retailersTransactions);
        return copy;
    }
}
