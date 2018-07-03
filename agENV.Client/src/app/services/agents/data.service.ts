import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { backend } from '../../../settings';
import { ConnectionService } from '../websocket/connection.service';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  agentTypes: any[];
  agents: any[];
  performatives: string[];

  agentsChanged: Subject<any[]> = new Subject<any[]>();

  constructor(
    private http: HttpClient,
    private conn: ConnectionService
  ) {
    conn.agentsChangedSubject.subscribe(x => this.fetchAgents());
    conn.agentTypesChangedSubject.subscribe(x => this.fetchAgentTypes());

    this.http.get<string[]>(`${backend}/messages`).subscribe(x=>{
      this.performatives = x;
    })

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
      this.agentsChanged.next(x);
    })
  }

  public startAgent(agent: any) {
    this.http.put<any>(`${backend}/agents/running/${agent.type.name}%3B${agent.type.module}/${agent.name}`, null).subscribe(x => { });
  }

  public stopAgent(aid: any) {
    this.http.delete<any>(`${backend}/agents/running/${aid.name}%2Bhost%3Balias%2B${aid.type.name}%3B${aid.type.module}/${aid.name}`).subscribe(x => { });
  }

  public postMessage(acl: any):Observable<any> {
    return this.http.post<any>(`${backend}/messages`, acl);
  }


}
