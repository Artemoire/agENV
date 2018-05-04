import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { WebsocketService } from '../../services/websocket.service';
import { LoggedinUserService } from '../../services/loggedin-user.service';
import { WSUPRequestType, wsupContexts } from '../../models/wsup-request.model';
import { UserDTO } from '../../models/user-dto.model';

@Component({
  selector: 'app-add-friend-dialog',
  templateUrl: './add-friend-dialog.component.html',
  styleUrls: ['./add-friend-dialog.component.css']
})
export class AddFriendDialogComponent implements OnInit {

  users: UserDTO[];
  loaded: boolean = false;

  constructor(
    private wss: WebsocketService,
    private user: LoggedinUserService,
    public dialogRef: MatDialogRef<AddFriendDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit() {
    this.wss.request(WSUPRequestType.READ, wsupContexts.users, null)
      .subscribe(x => {
        this.users = (x as UserDTO[]).filter(x => this.user.data.friends.indexOf(x.id) == -1);
        this.loaded = true;
      });
  }

}
