import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { JhiDateUtils } from 'ng-jhipster';

import { VisitorTrip } from './visitor-trip.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class VisitorTripService {

    private resourceUrl = 'visitors/api/visitor-trips';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(visitorTrip: VisitorTrip): Observable<VisitorTrip> {
        const copy = this.convert(visitorTrip);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(visitorTrip: VisitorTrip): Observable<VisitorTrip> {
        const copy = this.convert(visitorTrip);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<VisitorTrip> {
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
        entity.dateEntry = this.dateUtils
            .convertDateTimeFromServer(entity.dateEntry);
        entity.dateExit = this.dateUtils
            .convertDateTimeFromServer(entity.dateExit);
        entity.creditCardExpiry = this.dateUtils
            .convertDateTimeFromServer(entity.creditCardExpiry);
        entity.createdDate = this.dateUtils
            .convertDateTimeFromServer(entity.createdDate);
    }

    private convert(visitorTrip: VisitorTrip): VisitorTrip {
        const copy: VisitorTrip = Object.assign({}, visitorTrip);

        copy.dateEntry = this.dateUtils.toDate(visitorTrip.dateEntry);

        copy.dateExit = this.dateUtils.toDate(visitorTrip.dateExit);

        copy.creditCardExpiry = this.dateUtils.toDate(visitorTrip.creditCardExpiry);

        copy.createdDate = this.dateUtils.toDate(visitorTrip.createdDate);
        return copy;
    }
}
