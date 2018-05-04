import { Component, OnInit } from '@angular/core';
import { LoggedinUserService } from '../../services/loggedin-user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  model: any = {}; 
  loading: boolean = false;
  usernameError: boolean = false;
  otherError: boolean = false;

  constructor(
    public user: LoggedinUserService
  ) { }

  ngOnInit() {
  }

  onLogin() {
    this.loading = true;
    this.usernameError = false;
    this.otherError = false;
    this.user.login(this.model).subscribe((x)=>{
      this.loading = false;

    },y=>{
      this.loading = false;
      if (y.message == "Bad username or password")
      this.usernameError = true
      else
      this.otherError = true;
    });
  }

}
