import { Component, OnInit, ViewChild } from '@angular/core';
import { LogService } from '../../services/websocket/log.service';
import { ScrollbarComponent } from 'ngx-scrollbar';

@Component({
  selector: 'app-console-output',
  templateUrl: './console-output.component.html',
  styleUrls: ['./console-output.component.css']
})
export class ConsoleOutputComponent implements OnInit {

  @ViewChild(ScrollbarComponent) scrollRef: ScrollbarComponent;
  timeout: any;

  constructor(
    public log: LogService
  ) { }

  ngOnInit() {
    this.log.onReceive.subscribe(x=>{      
      if (this.timeout)
      clearTimeout(this.timeout);
      
      this.timeout = setTimeout(()=>{
        this.scrollRef.scrollToBottom();
        this.timeout = undefined;
      }, 100);
      
    })
  }

}
