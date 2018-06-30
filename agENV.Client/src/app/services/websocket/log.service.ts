import { Injectable } from '@angular/core';
import { ConnectionService } from './connection.service';

@Injectable({
  providedIn: 'root'
})
export class LogService {

  messages: string[] = [];

  constructor(
    private conn: ConnectionService
  ) {    
    conn.logSubject.subscribe(x=>{
      this.messages.push(x);
    })
   }
}
