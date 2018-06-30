import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { FlexLayoutModule } from '@angular/flex-layout';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavComponent } from './components/nav/nav.component';
import { MaterialModule } from './modules/material/material.module';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LayoutModule } from '@angular/cdk/layout';
import { AgentStartFormComponent } from './components/agent-start-form/agent-start-form.component';
import { AgentListComponent } from './components/agent-list/agent-list.component';
import { AgentMessageFormComponent } from './components/agent-message-form/agent-message-form.component';
import { HomeComponent } from './components/home/home.component';
import { UtilsRangePipe } from './pipes/utils-range.pipe';
import { ConsoleComponent } from './components/console/console.component';
import { ConsoleOutputComponent } from './components/console-output/console-output.component';

@NgModule({
  declarations: [
    AppComponent,
    NavComponent,
    AgentStartFormComponent,
    AgentListComponent,
    AgentMessageFormComponent,
    HomeComponent,
    UtilsRangePipe,
    ConsoleComponent,
    ConsoleOutputComponent
  ],
  imports: [
    BrowserModule,
    FlexLayoutModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    AppRoutingModule,
    MaterialModule,
    LayoutModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
