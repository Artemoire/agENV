import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-agent-list',
  templateUrl: './agent-list.component.html',
  styleUrls: ['./agent-list.component.css']
})
export class AgentListComponent implements OnInit {

  runningAgents = [
    { name: "A" },
    { name: "B" },
    { name: "C" },
    { name: "D" },
    { name: "A" },
    { name: "B" },
    { name: "C" },
    { name: "D" },
    { name: "A" },
    { name: "B" },
    { name: "C" },
    { name: "D" },
    { name: "A" },
    { name: "B" },
    { name: "C" },
    { name: "D" },
    { name: "A" },
    { name: "B" },
    { name: "C" },
    { name: "D" },
    { name: "A" },
    { name: "B" },
    { name: "C" },
    { name: "D" },
    { name: "A" },
    { name: "B" },
    { name: "C" },
    { name: "D" },
    { name: "A" },
    { name: "B" },
    { name: "C" },
    { name: "D" },
    { name: "A" },
    { name: "B" },
    { name: "C" },
    { name: "D" },
    { name: "A" },
    { name: "B" },
    { name: "C" },
    { name: "D" },
    { name: "A" },
    { name: "B" },
    { name: "C" },
    { name: "D" },
    { name: "A" },
    { name: "B" },
    { name: "C" },
    { name: "D" },
    { name: "A" },
    { name: "B" },
    { name: "C" },
    { name: "D" },
    { name: "A" },
    { name: "B" },
    { name: "C" },
    { name: "D" },
    { name: "A" },
    { name: "B" },
    { name: "C" },
    { name: "D" },
    { name: "A" },
    { name: "B" },
    { name: "C" },
    { name: "D" },
  ]

  displayedColumns: string[] = ['name'];

  constructor() { }

  ngOnInit() {
  }

}
