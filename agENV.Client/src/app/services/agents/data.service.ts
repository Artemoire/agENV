import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { backend } from '../../../settings';
import { ConnectionService } from '../websocket/connection.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  agentTypes: any[];
  agents: any[];

  constructor(
    private http: HttpClient,
    private conn: ConnectionService
  ) {
    conn.agentsChangedSubject.subscribe(x => this.fetchAgents());
    conn.agentTypesChangedSubject.subscribe(x => this.fetchAgentTypes());

    this.fetchAgentTypes();
    this.fetchAgents();
  }

  public fetchAgentTypes() {
    this.http.get<any[]>(`${backend}/agents/classes`).subscribe(x => {
      this.agentTypes = x;
    })
  }

  public fetchAgents() {
    this.http.get<any[]>(`${backend}/agents/running`).subscribe(x => {
      this.agents = x;
    })
  }

  public startAgent(agent: any) {
    this.http.put<any>(`${backend}/agents/running/${agent.type.name}%3B${agent.type.module}/${agent.name}`, null).subscribe(x => this.fetchAgents());
  }


}
