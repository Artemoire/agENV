import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AgentStartFormComponent } from './components/agent-start-form/agent-start-form.component';
import { AgentMessageFormComponent } from './components/agent-message-form/agent-message-form.component';
import { AgentListComponent } from './components/agent-list/agent-list.component';
import { HomeComponent } from './components/home/home.component';

const routes: Routes = [
  { path: "home", component: HomeComponent },
  { path: "start", component: AgentStartFormComponent },
  { path: "running", component: AgentListComponent },
  { path: "message", component: AgentMessageFormComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
