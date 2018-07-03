import { Component, OnInit } from '@angular/core';
import { DataService } from '../../services/agents/data.service';
import { MatSelectChange, MatSnackBar } from '@angular/material';

@Component({
  selector: 'app-agent-message-form',
  templateUrl: './agent-message-form.component.html',
  styleUrls: ['./agent-message-form.component.css']
})
export class AgentMessageFormComponent implements OnInit {

  loading: boolean = false;
  errorMessage: string;
  model: any = {
    receivers: []
  };

  constructor(
    private snackBar: MatSnackBar,
    public data: DataService
  ) { }

  ngOnInit() {
  }

  onMessage() {
    this.errorMessage = undefined;

    if (this.model.receivers.length == 0)
      this.errorMessage = "Receivers can't be empty";

    if (typeof (this.model.perform) == 'undefined')
      this.errorMessage = "Performative must not be empty";

    if (this.errorMessage)
      return;

    if (typeof (this.model.content) == 'undefined')
      this.model.content = '';

      console.log(this.model);
    this.data.postMessage(this.model).subscribe(x=>{
      this.snackBar.open('Message sent', 'Kewl', {duration: 1400});
      this.model = {
        receivers: []
      };  
    });
  }

  onSelectReceiver(ev: MatSelectChange) {
    this.model.receivers.push(ev.value);    
  }

  removeReceiver(aid: any) {
    this.model.receivers.splice(this.model.receivers.indexOf(aid), 1);
  }

}
