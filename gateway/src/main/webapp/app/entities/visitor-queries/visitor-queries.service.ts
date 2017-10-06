import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { JhiDateUtils } from 'ng-jhipster';

import { VisitorQueries } from './visitor-queries.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class VisitorQueriesService {

    private resourceUrl = 'visitors/api/visitor-queries';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(visitorQueries: VisitorQueries): Observable<VisitorQueries> {
        const copy = this.convert(visitorQueries);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(visitorQueries: VisitorQueries): Observable<VisitorQueries> {
        const copy = this.convert(visitorQueries);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<VisitorQueries> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
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
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.queryDate = this.dateUtils
            .convertDateTimeFromServer(entity.queryDate);
    }

    private convert(visitorQueries: VisitorQueries): VisitorQueries {
        const copy: VisitorQueries = Object.assign({}, visitorQueries);

        copy.queryDate = this.dateUtils.toDate(visitorQueries.queryDate);
        return copy;
    }
}
