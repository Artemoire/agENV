import { Component, OnInit } from '@angular/core';
import { LogService } from '../../services/websocket/log.service';

@Component({
  selector: 'app-console-output',
  templateUrl: './console-output.component.html',
  styleUrls: ['./console-output.component.css']
})
export class ConsoleOutputComponent implements OnInit {

  constructor(
    public log: LogService
  ) { }

  ngOnInit() {
  }

}
