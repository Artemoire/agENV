import { Injectable } from '@angular/core';
import { UserDTO } from '../models/user-dto.model';
import { WebsocketService } from './websocket.service';
import { LoggedinUserService } from './loggedin-user.service';
import { WSUPRequestType, wsupContexts } from '../models/wsup-request.model';

@Injectable()
export class FriendsService {

  data: UserDTO[];
  loaded: boolean = false;

  constructor(
    private wss: WebsocketService,
    private user: LoggedinUserService
  ) { }

  init() {
    this.wss.request(WSUPRequestType.READ, wsupContexts.friends, this.user.data)
      .subscribe(x => {
        console.log(x);
        this.loaded = true;
        this.data = x as UserDTO[];
      }, y => {
        this.loaded = true;
      })
  }

}
