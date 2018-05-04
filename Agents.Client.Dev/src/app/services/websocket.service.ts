import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { WSUPRequestType, WSUPRequest } from '../models/wsup-request.model';
import { Observable } from 'rxjs/Observable';
import { Subscriber } from 'rxjs/Subscriber';
import { WSUPResponse, WSUPResponseType } from '../models/wsup-response.model';

@Injectable()
export class WebsocketService {

  constructor() { }

  private ws: WebSocket;
  private postopen: ()=>void;
  private requests: any = {};

  public connect(postopen: ()=>void) {
    if (this.ws)
      return;
      this.postopen = postopen;
    this.ws = new WebSocket(environment.wsEndpoint);
    this.ws.onclose = this.onclose.bind(this);
    this.ws.onmessage = this.onmessage.bind(this);
    this.ws.onopen = this.onopen.bind(this);
  }

  disconnect(): any {
    if (!this.ws)
      return;
    this.ws.close();
    this.ws = null;
  }

  public request<T>(type: WSUPRequestType, context: string, data: any): Observable<T> {
    if (!this.ws)
      return null;

    var request = WSUPRequest.build(type, context, data);
    var requestAction = request.toActionString();
    this.ws.send(request.toString());
    return new Observable<T>(sub => {
      if (this.requests[requestAction])
        (this.requests[requestAction] as Subscriber<T>[]).push(sub);
      else this.requests[requestAction] = [sub];
      return () => {
        var subs = (this.requests[requestAction] as Subscriber<T>[])
        subs.splice(subs.indexOf(sub), 1)
      }
    });
  }

  private onopen(ev: MessageEvent) {
    if (this.postopen) {
      this.postopen();
      this.postopen = undefined;
    }      
  }

  private onclose(ev: MessageEvent) {
    console.log(ev);
  }

  private onmessage(ev: MessageEvent) {
    if ((ev.data as string).startsWith("RESPONSE")) {
      var response = WSUPResponse.parse(ev.data as string);
      var responseAction = response.toActionString();
      if (response != null) {
        if (this.requests[responseAction]) {
          var subs = (this.requests[responseAction] as Subscriber<any>[]).slice();
          for (var i = 0; i < subs.length; ++i) {
            var sub = subs[i];
            if (response.response == WSUPResponseType.SUCCESS) {
              sub.next(response.data);
            } else {
              sub.error(response.data);
            }
            if (sub)
              sub.unsubscribe();
          }
        }
      }
    }
  }

}