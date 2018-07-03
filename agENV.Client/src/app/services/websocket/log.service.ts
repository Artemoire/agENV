import { Injectable } from '@angular/core';
import { ConnectionService } from './connection.service';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LogService {

  messages: string[] = [];

  onReceive: Subject<any> = new Subject<any>();

  constructor(
    private conn: ConnectionService
  ) {    
    conn.logSubject.subscribe(x=>{
      this.messages.push(x);
      this.onReceive.next(null);
    })
   }
}
