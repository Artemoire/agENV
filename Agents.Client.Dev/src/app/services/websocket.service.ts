import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';

@Injectable()
export class WebsocketService {

  constructor() { }

  private ws: WebSocket;

  public connect() {
    if (this.ws)
      return;
    this.ws = new WebSocket(environment.wsEndpoint);
    this.ws.onmessage = this.onmessage.bind(this);
    this.ws.onopen = this.onopen.bind(this);
  }

  private onopen(ev: MessageEvent) {
    console.log(ev);
    console.log(this);
    this.ws.send("REQUEST CREATE\nLOGIN\n" + JSON.stringify(
      {
        name: "Dejan",
        surname: "Tot",
        username: "Artemoire2",
        password: "totjehot"
      }));
  }

  private onmessage(ev: MessageEvent) {
    console.log(ev);
    console.log(this);
    /*
      switch message type, forward to specific service
      in specific method do something like
      foreach observer .next
      on observer creation do something like
      return new Observable((sub)=>{
        var idx = this.subscibers.push(sub);
        return ()=>this.subsibers.splice(idx,1);
      });
    */
  }

}