import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
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

    this.http.get<string[]>(`http://${document.location.hostname}:8080/agENV/rest/messages`).subscribe(x=>{
      this.performatives = x;
    })

    this.fetchAgentTypes();
    this.fetchAgents();
  }

  public fetchAgentTypes() {
    this.http.get<any[]>(`http://${document.location.hostname}:8080/agENV/rest/agents/classes`).subscribe(x => {
      this.agentTypes = x;
    })
  }

  public fetchAgents() {
    this.http.get<any[]>(`http://${document.location.hostname}:8080/agENV/rest/agents/running`).subscribe(x => {
      this.agents = x;
      this.agentsChanged.next(x);
    })
  }

  public startAgent(agent: any) {
    this.http.put<any>(`http://${document.location.hostname}:8080/agENV/rest/agents/running/${agent.type.name}%3B${agent.type.module}/${agent.name}`, null).subscribe(x => { });
  }

  public stopAgent(aid: any) {
    this.http.delete<any>(`http://${document.location.hostname}:8080/agENV/rest/agents/running/${aid.name}%7C${aid.host.address}%3B${aid.host.alias}%7C${aid.type.name}%3B${aid.type.module}`).subscribe(x => { });
  }

  public postMessage(acl: any):Observable<any> {
    return this.http.post<any>(`http://${document.location.hostname}:8080/agENV/rest/messages`, acl);
  }


}
