import { Component } from '@angular/core';
import { LogService } from './services/websocket/log.service';
import { DataService } from './services/agents/data.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  
  constructor(
    private logService: LogService,
    private dataService: DataService
  ) {}

}
