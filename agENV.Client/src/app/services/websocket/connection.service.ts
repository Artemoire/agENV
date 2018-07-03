import { Injectable } from '@angular/core';
import { wsEndpoint } from '../../../settings';
import { Subject, Subscription, PartialObserver } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ConnectionService {


  private ws: WebSocket;

  connectionTry: number = 0;
  status: number = 0; // 0 = connecting, 1 = connected, 2 = fail

  logSubject = new Subject<string>();
  agentsChangedSubject = new Subject<any>();
  agentTypesChangedSubject = new Subject<any>();

  constructor() { }

  public connect() {
    console.log("Connecting...");
    if (this.ws)
      return;
    this._connect();
  }

  private _connect() {
    this.status = 0;
    this.ws = new WebSocket(wsEndpoint);
    this.ws.onopen = this.onOpen.bind(this);
    this.ws.onclose = this.onClose.bind(this);
    this.ws.onmessage = this.onMessage.bind(this);
    this.ws.onerror = this.onError.bind(this);
  }

  onOpen(e: MessageEvent) {
    console.log("Connected!");
    this.status = 1;
  }

  onClose(e: CloseEvent) {
    if (this.connectionTry == 0) {
      this.status = 2;
      setTimeout(() => this._connect(), 2000);
    }
  }

  onMessage(e: MessageEvent) {
    let data = e.data as string;
    let idx = data.indexOf("|");
    if (idx > 0) {
      let messageType = Number.parseInt(data.substring(0, idx)) as MessageType;
      switch (messageType) {
        case MessageType.LOG:
          this.logSubject.next(JSON.parse(data.substring(idx + 1)));
          break;
        case MessageType.REFRESH_RUNNING:
          this.agentsChangedSubject.next(null);
          break;
        case MessageType.REFRESH_TYPES:
          this.agentTypesChangedSubject.next(null);
          break;

        default:
          break;
      }

    }
  }

  onError(e: Event) {
    if (this.ws.readyState == WebSocket.CLOSED) {
      this.ws = null;
      this.connectionTry = this.connectionTry < 2 ? this.connectionTry + 1 : 2;
      this.status = 2;
      if (this.connectionTry < 2)
        setTimeout(() => this._connect(), 1000);
    }
  }

}

enum MessageType {
  LOG,
  REFRESH_RUNNING,
  REFRESH_TYPES
}
