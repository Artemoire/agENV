import { Component, OnInit } from '@angular/core';
import { LoggedinUserService } from '../../services/loggedin-user.service';
import { FriendsService } from '../../services/friends.service';
import { MatDialog } from '@angular/material';
import { AddFriendDialogComponent } from '../add-friend-dialog/add-friend-dialog.component';

@Component({
  selector: 'app-inbox',
  templateUrl: './inbox.component.html',
  styleUrls: ['./inbox.component.css']
})
export class InboxComponent implements OnInit {

  selectedFriend: number = -1;

  constructor(
    private dialog: MatDialog,
    public user: LoggedinUserService,
    public friends: FriendsService
  ) { }

  ngOnInit() {
    this.friends.init();
  }

  onAddFriend() {
    let dialogRef = this.dialog.open(AddFriendDialogComponent);
    dialogRef.afterClosed().subscribe(x=>{
      if (x) {
        this.user.data.friends.push(x.id);
        this.friends.data.push(x);
      }
    })
  }

}
