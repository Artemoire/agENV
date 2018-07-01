import { Component, OnInit } from '@angular/core';
import { ConnectionService } from '../../services/websocket/connection.service';

@Component({
  selector: 'app-console',
  templateUrl: './console.component.html',
  styleUrls: ['./console.component.css']
})
export class ConsoleComponent implements OnInit {

  expanded: boolean = false;

  constructor(
    public conn: ConnectionService
  ) { }

  ngOnInit() {
    this.conn.connect();
  }

}
