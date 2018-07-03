import { Component, OnInit } from '@angular/core';
import { DataService } from '../../services/agents/data.service';
import { MatSelectChange } from '@angular/material';
import { SenderTransferService } from '../../services/sender-transfer.service';

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
    public data: DataService,
    private receiverTransfer: SenderTransferService
  ) { }

  ngOnInit() {
    var rcvrs = this.receiverTransfer.get();
    if (rcvrs && rcvrs.length != 0)
      this.model.receivers = rcvrs;
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

    this.data.postMessage(this.model).subscribe(x => {
      this.model.content = '';
    });
  }

  onSelectReceiver(ev: MatSelectChange) {
    this.model.receivers.push(ev.value);
  }

  removeReceiver(aid: any) {
    this.model.receivers.splice(this.model.receivers.indexOf(aid), 1);
  }

}
