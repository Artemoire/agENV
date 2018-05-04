import { Injectable } from '@angular/core';
import { WSUPRequestType, WSUPRequest } from '../models/wsup-request.model';
import { Observable } from 'rxjs/Observable';
import { Subscriber } from 'rxjs/Subscriber';
import { WSConnectionProvider } from './ws-connection.provider';
import { WSUPResponse, WSUPResponseType } from '../models/wsup-response.model';

export class TransientWSRequestProvider {

  constructor() { }

  private ws: WebSocket;
  private sub: Subscriber<any>;
  private payload: WSUPRequest;

  public request<T>(type: WSUPRequestType, context: string, data: any): Observable<T> {
    if (this.ws)
      return null;
    this.ws = WSConnectionProvider.connect();
    this.ws.onmessage = this.onmessage.bind(this);
    this.ws.onopen = this.onopen.bind(this);
    this.payload = WSUPRequest.build(type, context, data);
    return new Observable<T>(sub => {
      this.sub = sub;
      return () => { this.sub = null; }
    })
  }

  private onopen(ev: MessageEvent) {
    this.ws.send(this.payload.toString());
  }

  private onmessage(ev: MessageEvent) {
    var response = WSUPResponse.parse(ev.data as string);
    if (response != null) {
      if (response.type == this.payload.type && response.context == this.payload.context) {
        if (response.response == WSUPResponseType.SUCCESS) {
          this.sub.next(response.data);
        } else {
          this.sub.error(response.data);
        }
      }
    }
    if (this.sub)
      this.sub.unsubscribe();
    this.ws.close();
    this.sub = null;
    this.ws = null;
  }

}
