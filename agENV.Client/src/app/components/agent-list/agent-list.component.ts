import { Component, OnInit } from '@angular/core';
import { DataService } from '../../services/agents/data.service';
import { MatTableDataSource } from '@angular/material';
import { SelectionModel } from '@angular/cdk/collections';
import { ConnectionService } from '../../services/websocket/connection.service';

@Component({
  selector: 'app-agent-list',
  templateUrl: './agent-list.component.html',
  styleUrls: ['./agent-list.component.css']
})
export class AgentListComponent implements OnInit {  

  displayedColumns: string[] = ['select', 'name', 'typeModule', 'typeName', 'hostAlias', 'hostAddress' ];
  dataSource:MatTableDataSource<any>;
  selection = new SelectionModel<any>(true, []);

  constructor(
    public data: DataService
  ) { }

  ngOnInit() {
    this.dataSource = new MatTableDataSource<any>(this.data.agents);

    this.data.agentsChanged.subscribe(x=>{
      this.dataSource = new MatTableDataSource<any>(this.data.agents);
    })
  }


  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    this.isAllSelected() ?
        this.selection.clear() :
        this.dataSource.data.forEach(row => this.selection.select(row));
  }

  onDelete() {
    for(let aid of this.selection.selected.slice()) {
      this.data.stopAgent(aid);
    }
  }

}
