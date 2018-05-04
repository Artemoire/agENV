import { Component, OnInit } from '@angular/core';
import { TransientWSRequestProvider } from '../../providers/transient-ws-request.provider';
import { WSUPRequestType, wsupContexts } from '../../models/wsup-request.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  model: any = {};
  usernameError: boolean = false;
  otherError: boolean = false;
  loading: boolean = false;

  constructor(private router: Router) { }

  ngOnInit() {
  }

  onRegister() {
    this.loading = true;
    this.usernameError = false;
    this.otherError = false;
    new TransientWSRequestProvider().request<any>(WSUPRequestType.CREATE, wsupContexts.register,
      this.model).subscribe(x => {        
        this.loading = false;
        this.router.navigateByUrl("/");
      }, y => {
        this.loading = false;
        if (y.message == "Username already exists")
          this.usernameError = true;
        else
          this.otherError = true;
      });
  }

}
