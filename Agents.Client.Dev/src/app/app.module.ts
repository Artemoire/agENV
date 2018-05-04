import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { WebsocketService } from './services/websocket.service';
import { MaterialModule } from './modules/material/material.module';
import { FormsModule } from '@angular/forms';
import { LoginComponent } from './components/login/login.component';
import { AppRoutingModule } from './app-routing.module';
import { RegisterComponent } from './components/register/register.component';
import { HomeComponent } from './components/home/home.component';
import { InboxComponent } from './components/inbox/inbox.component';
import { LoggedinUserService } from './services/loggedin-user.service';
import { FriendsService } from './services/friends.service';
import { ConversationService } from './services/conversation.service';
import { AddFriendDialogComponent } from './components/add-friend-dialog/add-friend-dialog.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    InboxComponent,
    AddFriendDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    MaterialModule
  ],
  entryComponents: [
    AddFriendDialogComponent
  ],
  providers: [WebsocketService, LoggedinUserService, FriendsService, ConversationService],
  bootstrap: [AppComponent]
})
export class AppModule { }
