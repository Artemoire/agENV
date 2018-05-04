import { Injectable } from '@angular/core';
import { UserDTO } from '../models/user-dto.model';
import { WebsocketService } from './websocket.service';
import { WSUPRequestType, wsupContexts } from '../models/wsup-request.model';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class LoggedinUserService {

  isLoggedIn: boolean = false;
  data: UserDTO;

  constructor(
    private wss: WebsocketService
  ) { }

  public login(model: any): Observable<any> {
    if (this.isLoggedIn)
      return;
    var loginSub;
    var loginO = new Observable<any>(sub => {
      loginSub = sub;
    })
    this.wss.connect(() => {
      this.wss.request(WSUPRequestType.CREATE, wsupContexts.login, model)
        .subscribe((x: UserDTO) => {
          this.data = x;
          this.isLoggedIn = true;
          loginSub.next(x);
        }, y => {
          this.wss.disconnect();
          loginSub.error(y);
        })
    });
    return loginO;
  }

}
