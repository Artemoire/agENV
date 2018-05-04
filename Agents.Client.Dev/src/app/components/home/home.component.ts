import { Component, OnInit } from '@angular/core';
import { WSUPRequest, WSUPRequestType, wsupContexts } from '../../models/wsup-request.model';
import { TransientWSRequestProvider } from '../../providers/transient-ws-request.provider';
import { LoggedinUserService } from '../../services/loggedin-user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(
    public user: LoggedinUserService
  ) { }

  ngOnInit() {
    // new TransientWSRequestProvider().request<any>(WSUPRequestType.CREATE, wsupContexts.register,
    //   {
    //     name: "Dejan",
    //     surname: "Tot",
    //     username: "Artemoire3",
    //     password: "totjehot"
    //   }).subscribe(x=>{
    //     console.log("registered:" + x);
    //   }, y=> {
    //     console.log(y);
    //   });
  }

}
