import { Component, OnInit } from '@angular/core';
import { DataService } from '../../services/agents/data.service';

@Component({
  selector: 'app-agent-list',
  templateUrl: './agent-list.component.html',
  styleUrls: ['./agent-list.component.css']
})
export class AgentListComponent implements OnInit {  

  displayedColumns: string[] = ['name', 'typeModule', 'typeName', 'hostAlias', 'hostAddress' ];

  constructor(
    public data: DataService
  ) { }

  ngOnInit() {
  }

}
